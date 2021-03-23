import jdk.jshell.execution.Util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Graph {
    private int[][] graph;
    private int size;
    private Lock readLock, writeLock;

    public Graph() {
        graph = Utils.generateStartingGraph(Utils.STARTING_SIZE);
        this.size = Utils.STARTING_SIZE;
        ReadWriteLock tempLock = new ReentrantReadWriteLock();
        this.readLock = tempLock.readLock();
        this.writeLock = tempLock.writeLock();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void changeTicketValue(int i, int j, int value) {
        writeLock.lock();
        if (value != Utils.INF && value >= 10 && i >= 0 && j >= 0 && i < this.size && j < this.size) {
            graph[i][j] = value;
            graph[j][i] = value;
        }
        writeLock.unlock();
    }

    public void printGraph() {
        readLock.lock();
        StringBuilder graphString = new StringBuilder("\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (graph[i][j] == Utils.INF) {
                    graphString.append(-1);
                } else {
                    graphString.append(graph[i][j]);
                }

                graphString.append(" ");
            }
            graphString.append('\n');
        }
        System.out.println(graphString);
        readLock.unlock();
    }

    public void changePathBetweenCities(int i, int j, int value) {
        writeLock.lock();
        graph[i][j] = value;
        graph[j][i] = value;

        writeLock.unlock();
    }

    public void addCity() {
        writeLock.lock();
        int[][] newGraph = Utils.createBiggerGraph(this.graph, this.size);
        this.size++;
        this.graph = newGraph;
        writeLock.unlock();
    }

    public void deleteCity(int deleteIndex) {
        if (this.size > 0) {
            writeLock.lock();
            int[][] newGraph = Utils.createSmallerGraph(this.graph, this.size, deleteIndex);
            this.size--;
            this.graph = newGraph;
            writeLock.unlock();
        }
    }

    private int findPathCostRec(int startPos, int destPost, boolean[] visited) {
        if (startPos == destPost)
            return 0;

        visited[startPos] = true;
        int ans = Utils.INF;

        for (int i = 0; i < this.size; i++) {
            if (graph[startPos][i] != Utils.INF && !visited[i]) {
                int curr = findPathCostRec(i, destPost, visited);

                if (curr < Utils.INF) {
                    ans = Math.min(ans, graph[startPos][i] + curr);
                }
            }
        }
        visited[startPos] = false;

        return ans;
    }

    public int findPathCost(int startPos, int destPost, boolean[] visited) {
        readLock.lock();
        int value = findPathCostRec(startPos, destPost, visited);
        readLock.unlock();
        return value;
    }

}
