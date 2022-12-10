package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.example.FileReader.reading;

public class Day8 {
    @RequiredArgsConstructor
    @ToString
    static class Tree {
        @Getter
        final int x, y, height;
        @Setter
        int scoreN, scoreS, scoreE, scoreW = 0;

        int total() {
            return scoreN * scoreS * scoreE * scoreW;
        }
    }

    public static void main(String[] args) {
        reading("day8/full.txt",
                fileLines -> {
                    NavigableMap<Integer, NavigableMap<Integer, Tree>> forest = buildForest(fileLines);
                    Set<Tree> visible = new HashSet<>();
                    leftToRight(forest, visible);
                    rightToLeft(forest, visible);
                    topToBottom(forest, visible);
                    bottomToTop(forest, visible);

                    print(forest, visible);

                    System.out.println("BEST SPOT");
                    Instant start = Instant.now();
                    findBestSpot(forest);
                    Instant end = Instant.now();

                    System.out.println("Took " + Duration.between(start, end).toMillis());
                });
    }

    private static void print(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest, Set<Tree> visible) {
        NavigableMap<Integer, NavigableMap<Integer, Tree>> visibleAsNavigableMap = visible.stream()
                .collect(collectingAndThen(
                        groupingBy(Tree::getY,
                                collectingAndThen(
                                        toMap(Tree::getX, identity()),
                                        TreeMap::new)),
                        TreeMap::new));
        forest.forEach((row, data) -> {
            if (visibleAsNavigableMap.containsKey(row)) {
                print(visibleAsNavigableMap.get(row));
            } else {
                System.out.println(".".repeat(data.values().size()));
            }
        });
        System.out.println("\n" + visible.size());
    }

    private static void findBestSpot(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest) {
        Tree best = null;

        for (int y = 1; y < forest.size() - 1; y++) {
            NavigableMap<Integer, Tree> treeRow = forest.get(y);
            for (int x = 1; x < treeRow.size() - 1; x++) {
                Tree tree = treeRow.get(x);
                tree.setScoreW(westViewDistance(tree, forest));
                tree.setScoreE(eastViewDistance(tree, forest));
                tree.setScoreN(northViewDistance(tree, forest));
                tree.setScoreS(southViewDistance(tree, forest));
                if (tree.total() > (best == null ? 0 : best.total())) {
                    best = tree;
                }
            }
        }

        if (best == null) {
            System.out.println("Unable to find a best score");
        } else {
            System.out.println(best);
            System.out.println(best.total());
        }
    }

    private static int northViewDistance(Tree tree, NavigableMap<Integer, NavigableMap<Integer, Tree>> forest) {
        int yIndex = tree.y - 1;
        while (yIndex > 0) {
            Tree other = forest.get(yIndex).get(tree.x);
            if (other.height >= tree.height) {
                break;
            }
            yIndex--;
        }
        return tree.y - yIndex;
    }

    private static int eastViewDistance(Tree tree, NavigableMap<Integer, NavigableMap<Integer, Tree>> forest) {
        int xIndex = tree.x + 1;
        NavigableMap<Integer, Tree> row = forest.get(tree.y);
        while (xIndex < row.size() - 1) {
            Tree other = row.get(xIndex);
            if (other.height >= tree.height) {
                break;
            }
            xIndex++;
        }
        return xIndex - tree.x;
    }

    private static int southViewDistance(Tree tree, NavigableMap<Integer, NavigableMap<Integer, Tree>> forest) {
        int yIndex = tree.y + 1;
        while (yIndex < forest.size() - 1) {
            Tree other = forest.get(yIndex).get(tree.x);
            if (other.height >= tree.height) {
                break;
            }
            yIndex++;
        }
        return yIndex - tree.y;
    }

    private static int westViewDistance(Tree tree, NavigableMap<Integer, NavigableMap<Integer, Tree>> forest) {
        int xIndex = tree.x - 1;
        while (xIndex > 0) {
            Tree other = forest.get(tree.y).get(xIndex);
            if (other.height >= tree.height) {
                break;
            }
            xIndex--;
        }
        return tree.x - xIndex;
    }

    private static void print(NavigableMap<Integer, Tree> rowData) {
        StringBuilder s = new StringBuilder();
        for (int x = 0; x < rowData.lastKey(); x++) {
            s.append(rowData.containsKey(x) ? rowData.get(x).height : " ");
        }
        System.out.println(s);
    }

    private static NavigableMap<Integer, NavigableMap<Integer, Tree>> buildForest(Stream<String> fileLines) {
        NavigableMap<Integer, NavigableMap<Integer, Tree>> forest = new TreeMap<>();
        List<String> lines = fileLines.toList();
        for (int y = 0; y < lines.size(); y++) {
            String thisLine = lines.get(y);
            NavigableMap<Integer, Tree> treesForLine = new TreeMap<>();
            for (int x = 0; x < thisLine.length(); x++) {
                treesForLine.put(x, new Tree(x, y, Integer.parseInt(thisLine.substring(x, x + 1))));
            }
            forest.put(y, treesForLine);
        }
        return forest;
    }

    private static void iterateColumn(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest,
                                      Set<Tree> visible,
                                      Iterable<Integer> iterable,
                                      int i) {
        Iterator<Integer> iterator = iterable.iterator();
        promoteVisible(new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Tree next() {
                return forest.get(iterator.next()).get(i);
            }
        }, visible);
    }

    private static void bottomToTop(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest, Set<Tree> visible) {
        NavigableMap<Integer, Tree> topRow = forest.firstEntry().getValue();
        for (int i = topRow.firstKey(); i < topRow.lastKey(); i++) {
            iterateColumn(forest, visible, forest.descendingKeySet(), i);
        }
    }

    private static void topToBottom(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest, Set<Tree> visible) {
        int startIndex = forest.firstEntry().getValue().firstKey();
        int endIndex = forest.firstEntry().getValue().lastKey();
        for (int i = startIndex; i < endIndex; i++) {
            iterateColumn(forest, visible, forest.navigableKeySet(), i);
        }
    }

    private static void leftToRight(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest, Set<Tree> visible) {
        forest.values().forEach(row -> promoteVisible(row.values().iterator(), visible));
    }

    private static void rightToLeft(NavigableMap<Integer, NavigableMap<Integer, Tree>> forest, Set<Tree> visible) {
        forest.values().forEach(row -> {
            Iterator<Integer> iterator = row.navigableKeySet().descendingIterator();
            promoteVisible(new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public Tree next() {
                    return row.get(iterator.next());
                }
            }, visible);
        });
    }

    private static void promoteVisible(Iterator<Tree> line, Set<Tree> visibleTrees) {
        int maxHeightTracker = -1;
        while (line.hasNext()) {
            Tree candidate = line.next();
            if (candidate.getHeight() > maxHeightTracker) {
                maxHeightTracker = candidate.getHeight();
                visibleTrees.add(candidate);
            }
        }
    }
}
