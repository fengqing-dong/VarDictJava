
# echo $GRADLE_USER_HOME
# /mnt/Taurus/private/fengqing/nextflow/gradle_bin/gradle-5.5.1/bin/wrapper/dists/gradle-7.4.2-bin/48ivgl02cpt2ed3fh9dbalvx8/gradle-7.4.2/bin/gradle jar

./gradlew clean installDist
./gradlew distTar

# run
/mnt/Taurus/private/fengqing/software/miniconda3/envs/panel/bin/java -jar /mnt/Taurus/private/fengqing/software/VarDictJava/build/libs/VarDict-1.8.3.jar   -G /mnt/Taurus/private/fengqing/databases/human/genecode/GRCh38/Release41/GRCh38.p13.genome.fa -N L23014020CF -b /mnt/Taurus/private/fengqing/temp/vardict_test/a2.bam -I 50 -L 50 -U -f 0.005 -r 3 -P 5 -o 2 -q 25 -O 30 -c 1  -S 2 -E 3 -g 4 -z 0 -B 2 -v -m 4 -X 2 -th 3 -3 --nosv -k 0  -F 0x304 /mnt/Taurus/private/fengqing/temp/vardict_test/ALK.bed | /mnt/Taurus/private/fengqing/software/miniconda3/envs/panel/bin/Rscript /mnt/Taurus/private/fengqing/software/VarDictJava/VarDict/teststrandbias.R           | /mnt/Taurus/private/fengqing/software/miniconda3/envs/panel/bin/perl /mnt/Taurus/private/fengqing/software/VarDictJava/VarDict/var2vcf_valid.pl           -N L23001085CF -A -E -f  0.005            >  L23001085CF.vardict.raw.snvindel.vcf