import java.util.Random;

public class Utils {
    public static final int STARTING_SIZE = 5;
    public static int INF = Integer.MAX_VALUE;

    public static int[][] generateStartingGraph(int size) {
        int[][] graph = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                graph[i][j] = INF;
            }
        }

        graph[0][1] = 10;
        graph[1][2] = 15;
        graph[1][4] = 20;

        graph[1][0] = 10;
        graph[2][1] = 15;
        graph[4][1] = 20;

        return graph;
    }

    public static int[][] createBiggerGraph(int[][] smallGraph, int sizeSmall) {
        int sizeBig = sizeSmall + 1;
        int[][] bigGraph = new int[sizeBig][sizeBig];

        for (int i = 0; i < sizeBig; i++) {
            for (int j = 0; j < sizeBig; j++) {
                if (i < sizeSmall && j < sizeSmall) {
                    bigGraph[i][j] = smallGraph[i][j];
                } else {
                    bigGraph[i][j] = INF;
                }
            }
        }

        return bigGraph;
    }

    public static int[][] createSmallerGraph(int[][] bigGraph, int sizeBig, int indexToDelete) {
        int sizeSmall = sizeBig - 1;
        int[][] smallGraph = new int[sizeSmall][sizeSmall];

        for (int i = 0; i < sizeBig; i++) {
            bigGraph[i][indexToDelete] = -2;
            bigGraph[indexToDelete][i] = -2;
        }

        int deletedRowFlag = 0;
        int deletedColumnFlag = 0;

        for (int i = 0; i < sizeSmall; i++) {
            for (int j = 0; j < sizeSmall; j++) {
                if (i == indexToDelete) {
                    deletedRowFlag = 1;
                    break;
                }
                if (j == indexToDelete) {
                    deletedColumnFlag = 1;
                }
                smallGraph[i][j] = bigGraph[i + deletedRowFlag][j + deletedColumnFlag];
            }
            deletedColumnFlag = 0;
        }

        if (indexToDelete == sizeSmall) {
            return smallGraph;
        }

        for (int i = 0; i < sizeSmall; i++) {
            if (i == indexToDelete) {
                deletedColumnFlag = 1;
            }
            smallGraph[indexToDelete][i] = bigGraph[indexToDelete + 1][i + deletedColumnFlag];
        }

        return smallGraph;
    }

    public static void startProgram(Graph graph) {
        Random rand = new Random();
        Thread threadTicketValueChange = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int randI = rand.nextInt(graph.getSize());
                int randJ = rand.nextInt(graph.getSize());
                int value = rand.nextInt(89) + 10;
                while (randJ == randI) {
                    randJ = rand.nextInt(graph.getSize());
                }

                graph.changeTicketValue(randI, randJ, value);
                System.out.println("Thread -1- has changed value of [" + randI + ", " + randJ + "] to: " + value);
            }
        });

        Thread addDeleteCity = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean option = rand.nextBoolean();

                if (option) {
                    graph.addCity();

                    System.out.println("Thread -3- has added a new city ");
                } else {
                    int deleteIndex = rand.nextInt(graph.getSize());
                    graph.deleteCity(deleteIndex);
                    System.out.println("Thread -3- has deleted a city at index: " + deleteIndex);
                }
            }
        });

        Thread addDeletePath = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean option = rand.nextBoolean();

                int randI = rand.nextInt(graph.getSize());
                int randJ = rand.nextInt(graph.getSize());

                if (option) {
                    int value = rand.nextInt(89) + 10;
                    graph.changePathBetweenCities(randI, randJ, value);
                    System.out.println("Thread -2- has added a new path to [" + randI + ", " + randJ + "], with value: " + value);
                } else {
                    graph.changePathBetweenCities(randI, randJ, INF);
                    System.out.println("Thread -2- has deleted a path at [" + randI + ", " + randJ + "]");
                }
            }
        });

        Runnable findPath = () -> {
            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int startPos = rand.nextInt(graph.getSize());
                int destPos = rand.nextInt(graph.getSize());
                int pathCost = graph.findPathCost(startPos, destPos, new boolean[graph.getSize()]);

                if (pathCost == INF) {
                    pathCost = 0;
                }

                System.out.println("Thread of group [4] found the path between " + startPos + " - "
                        + destPos + " and the value is: "
                        + pathCost);
            }
        };

        Thread findPath1 = new Thread(findPath);
        Thread findPath2 = new Thread(findPath);
        Thread printGraph = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                graph.printGraph();
            }
        });


        threadTicketValueChange.start();
        addDeleteCity.start();
        addDeletePath.start();
        findPath1.start();
        findPath2.start();
        printGraph.start();
    }
}
