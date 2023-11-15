package com.astrazeneca.vardict.variations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.astrazeneca.vardict.variations.VarsCount;
/**
 * Intermediate variant structure
 */
public class Variation {
    /**
     * Variant count $cnt
     */
    public int varsCount;

    /**
     * Variant count on forward strand $dirPlus
     */
    public int varsCountOnForward;

    /**
     * Variant count on reverse strand $dirMinus
     */
    public int varsCountOnReverse;

    /**
     * Sum of variant positions in read $pmean
     */
    public double meanPosition;

    /**
     * Sum of base qualities for variant $qmean
     */
    public double meanQuality;

    /**
     * Sum of mapping qualities for variant $Qmean
     */
    public double meanMappingQuality;

    /**
     * Sum of number of mismatches for variant  $nm
     */
    public double numberOfMismatches;

    /**
     * Number of low-quality reads with the variant $locnt
     */
    public int lowQualityReadsCount;

    /**
     * Number of high-quality reads with the variant $hicnt
     */
    public int highQualityReadsCount;

    /**
     * Flags that is true when variant is found in at least 2 different positions $pstd
     */
    public boolean pstd;

    /**
     * Flags that is 1 when variant is read with at least 2 different qualities $qstd
     */
    public boolean qstd;

    /**
     * Position in read for previous instance of this variant (used for pstd) $pp
     */
    public int pp;

    // 定义突变的结构体
    public int dup_paired = 0;
    public int dup_single = 0;
    public int uniq_paired = 0;
    public int uniq_single = 0;
    public int paired = 0;
    public int single = 0;


    //  定义hashmap
    public Map<String,  List<VarsCount>> varsCounts;

    /**
     * Base quality for previous instance of this variant (used for qstd)  $pq
     */
    public double pq;

    /**
     * Adjusted count for indels due to local realignment $extracnt
     */
    public int extracnt;

    /**
     * Increment count for direction
     * @param dir false for forward strand, true for reverse strand
     */
    public void incDir(boolean dir) {
        if (dir)
            this.varsCountOnReverse++;
        else
            this.varsCountOnForward++;
    }

    public void updateVarDupOrPaired() {
        Map<String, Integer> singleReadKey = new TreeMap<>();
        Map<String, Integer> pairedReadKey = new TreeMap<>();
        String key;
        //  skip  if varsCounts is empty
        if (varsCounts == null || varsCounts.size() == 0){
            return;
        }
        for (Map.Entry<String, List<VarsCount>> entry : varsCounts.entrySet()) {
            List<VarsCount> reads = new ArrayList<>();
            reads = entry.getValue();
            if (reads.size() == 2){
                this.paired++;
                VarsCount var1 = reads.get(0);
                VarsCount var2 = reads.get(1);
                if (var1.varStart >= var2.varStart){
                    key = var1.varStart + "_" + var1.varEnd + "_" + var2.varStart + "_" + var2.varEnd;
                }else{
                    key = var2.varStart + "_" + var2.varEnd + "_" + var1.varStart + "_" + var1.varEnd;
                }
              if (!pairedReadKey.containsKey(key)){
                    pairedReadKey.put(key, 1);
                }else{
                   pairedReadKey.put(key, pairedReadKey.get(key) + 1); 
                }
            }else{
                this.single++;
                key = reads.get(0).varStart + "_" + reads.get(0).varEnd;
                if (!singleReadKey.containsKey(key)){
                    singleReadKey.put(key, 1);
                }else{
                   singleReadKey.put(key, singleReadKey.get(key) + 1); 
                }
            }
        }
        for (Map.Entry<String, Integer> entry1 : singleReadKey.entrySet()) {
            if (entry1.getValue() == 1){
                this.uniq_single++;
            }else{
                this.dup_single++;
            }
        }
        for (Map.Entry<String, Integer> entry1 : pairedReadKey.entrySet()) {
            if (entry1.getValue() == 1){
                this.uniq_paired++;
            }else{
                this.dup_paired++;
            }
        }
        this.varsCounts = null;
    }
    /**
     * Decrement count for direction
     * @param dir false for forward strand, true for reverse strand
     */
    public void decDir(boolean dir) {
        if (dir)
            this.varsCountOnReverse--;
        else
            this.varsCountOnForward--;
    }

    /**
     * Get variant count for direction
     * @param dir false for forward strand, true for reverse strand
     * @return variant count
     */
    public int getDir(boolean dir) {
        if (dir)
            return this.varsCountOnReverse;
        return this.varsCountOnForward;
    }

    /**
     * Add count for direction
     * @param dir false for forward strand, true for reverse strand
     * @param add amount of counts need to be added in the specific direction
     */
    public void addDir(boolean dir, int add) {
        if (dir)
            this.varsCountOnReverse += add;
        else
            this.varsCountOnForward += add;
    }

    /**
     * Subtract count for direction
     * @param dir false for forward strand, true for reverse strand
     * @param sub amount of counts need to be subtracted in the specific direction
     */
    public void subDir(boolean dir, int sub) {
        if (dir)
            this.varsCountOnReverse -= sub;
        else
            this.varsCountOnForward -= sub;
    }

    @Override
    public String toString() {
        return "Variation{" +
                "varsCount=" + varsCount +
                ", varsCountOnForward=" + varsCountOnForward +
                ", varsCountOnReverse=" + varsCountOnReverse +
                ", meanPosition=" + meanPosition +
                ", meanQuality=" + meanQuality +
                ", meanMappingQuality=" + meanMappingQuality +
                ", numberOfMismatches=" + numberOfMismatches +
                ", lowQualityReadsCount=" + lowQualityReadsCount +
                ", highQualityReadsCount=" + highQualityReadsCount +
                ", pstd=" + pstd +
                ", qstd=" + qstd +
                ", pp=" + pp +
                ", pq=" + pq +
                ", extracnt=" + extracnt +
                '}';
    }
}
