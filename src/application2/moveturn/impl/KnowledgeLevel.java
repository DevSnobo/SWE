package application2.moveturn.impl;

import java.util.Arrays;

public class KnowledgeLevel {
    private int[] knowledgeLevelTable;

    public KnowledgeLevel() {
        this.knowledgeLevelTable = new int[4];
    }

    @Override
    public String toString() {
        return "KnowledgeLevel{" +
                "knowledgeLevelTable=" + Arrays.toString(knowledgeLevelTable) +
                '}';
    }
}
