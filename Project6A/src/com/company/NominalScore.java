package com.company;

class NominalScore implements Comparable<NominalScore> {
    public String id;
    public double score;

    public int compareTo(NominalScore ns){
        return (int)(this.score - ns.score);
    }
}
