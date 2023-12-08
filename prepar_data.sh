#!/usr/bash

path=/mnt/Taurus/private/fengqing/temp/test_paired
bam1=/mnt/Taurus/private/fengqing/run/panel/HRD/test_pipe/alignment/T430V208.marked_dup.BQSR.abra.bam
bam2=/mnt/Taurus/private/fengqing/run/panel/HRD/test_pipe/alignment/T430V206.marked_dup.BQSR.abra.bam


# chr1	20965680
# chr1	30036652
# chr1	46248523
# chr3	124605586
chrom=chr1
pos=4174745
start=$((${pos}-100))
end=$((${pos}+100))


/mnt/Taurus/private/fengqing/software/miniconda3/envs/panel/bin/samtools view -b -o ${path}/tumor.bam  ${bam1} "${chrom}:${start}-${end}"
/mnt/Taurus/private/fengqing/software/miniconda3/envs/panel/bin/samtools view -b -o ${path}/normal.bam ${bam2} "${chrom}:${start}-${end}" 

samtools index  ${path}/tumor.bam 
samtools index ${path}/normal.bam

printf  "${chrom}\t$((${pos}-1))\t$((${pos}+1))" > ${path}/region.bed