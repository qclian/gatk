package org.broadinstitute.hellbender.tools.spark.sv;

import org.broadinstitute.hellbender.utils.SimpleInterval;
import org.broadinstitute.hellbender.utils.bwa.BwaMemIndexCache;
import org.broadinstitute.hellbender.utils.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ContigAlignerTest extends BaseTest {

    private ContigAligner contigAligner;

    @BeforeClass
    public void setup() throws Exception {
        contigAligner = new ContigAligner(b37_reference_20_21+".img");
    }

    @Test
    public void testAlignContigs() throws Exception {

        // data taken from G94982 NA12878 assembly for breakpoint interval 21:27373582-27375176, which contains a small inversion
        final List<String> contigsData = new ArrayList<>();
        contigsData.add(">contig-16 169 0");
        contigsData.add("ACATGTTAATTAAGTAAATAGTATAAGCTACACGTTGAGCCTACAACTCTTGCTGGAGTCTCCAGCTGTAAGAGCCTTAACTCCTTCCTTTCATAAAGGCCAAGAAAAGAGTGAGTTAGTCCCTATTCGGGGAGTGTTGGTTGGTTCTCCCAATCCTGTTAGGGGCAGT");
        contigsData.add(">contig-5 151 0");
        contigsData.add("GAGTCTATATATTAAATAAAAGTGGTAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGATTCATGATAACTATGTGCGCTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCAC");
        contigsData.add(">contig-11 192 0");
        contigsData.add("GTGAACATCACAGCTCTGCAGATGCACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAAGTGGTAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGATTCATGATAACTATGTGCCCTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCA");
        contigsData.add(">contig-9 2009 0");
        contigsData.add("CATGAAGTACATCTATAACAACGTATTATTAATTCTGCTATTTAATTGATATTTATATATTAAAAATCTGTATTATTTGTATATATTTACATACACATTAAGTACAACTATAACAATGTATTATTTAATTATGCTACTTAATTGATATTTATGTATTAAAAATCTGTATTATTTGTATATTTTGTATATTTACCTGACCCTTGGGCAGGAATGTTCCAAACAATAAAACGCAGTCAATCAACTCCAACTGATTAATACACACCACGCACTGTTATACATTAAAACACATCTTACCTGTCAACATGTCTTGCCACCCTCCCCAAGATCAGTTTCACTCATCCCTGATTGCCTCAAAGTCAATCAGTGACCCATCCAATTGCATGTATAAAGTCAATCCTGAGTAGAATTATAACTCTACCTCCCATGTATATCCCATTTTTATTGGAAGTGAAACAACAGCCCAATTCTCTCTCTGGGTTTGTGCCCTGCCCTTGCTAGACTGACGCCTTAGCACACACAGATGTCTTCTTAGTTTTACTGACAGAGGAAAAAACACCTTTCTAAAAGATGAAAGCCACGCATCACCCATTACTTCCAAGTTAGGAATATTCAACAGATTCTTTTTTTCTTTTTATGAGACAGGGTCTTGCACTGCCGCCCAGTGGCACAATCTTGGCTCACTGCAACCTCTACCTCCTGAGTTCAAGTGATTCTTGCGCCTCAGCCTCCCAAGTAGCTGGGATTACCCTTGTACCAGCACGCCTGGCTAATTTTTTGGTATTTTTAGTAGAGATGGGGTTTCACCAGGTAGGCCAGGCTGGTCTTGAACTCCTGGCCTCAAGTGATACACCTGCCTCAGCCTCTCTAAGTGCTGGGATTACAGGCGTGAGCCACTGCCACTGGTCCTTCAACAGATTGATTCTAATTAGCCAATCAAAGACAAGGATCCACAATGTCAATACAAATGGGGACTAACTATTGTTTTACTTCCCTATACACGTTGCTCTTTCAGTAGATGCAATAAAGTACTGGTAAAACCAGAGGTGGCTACCATCACGATGATGTCAACAGGAGGGACAGTCAGCACTAAGCCCAGAAGGTGTCAAACACTCCGCAGGAGAAATGCGCCATGCAACGGACATGAAGATGATCTGACACTCTTCACGTGGTTTTCAGATGGAAACGTGGCTACGAAAGCATCAACCTCATTATCATCCATCATTAAGGCCATCTCACTCAGTACTGCTGCTTTCAAAGTCCACGCTCCCAAAGCAAATTGGATTTCTGTACACAATACTCTTACAGGATGAAACCCAACCAACTTGTTGACGTAAGTATCCCATCTACTTCGCAATTTTATTTATCTTCCAAAATTAAAGGACTGGCACCCTGATTTATTAAAAGTGAATTGGTTCTAGGGACCATATCCCCTCTGAGTTACTGACAGAGCAGCTTCTGGCCTGTGAAGCTCAAAGCCATGCCTAGATGTGAGGATCCCATCAACTATAGCCAGCAGTGGTCTCTGCTCCCAGAGCTTACCTTCAGTTGTAACAAACATCTACAGCTTCACTTTGCTTTCCTTCTCTGTATCTCCTTCACCAATGTGTACATATTCATGTCACATGCTCTTTAACGTTTCTAAGACACAACAATACCCATTATTAATCTCTTACTCAGGCAGTGTTAATTCCAATAAGACTGCAAGGCAGCAGTGCCTCCAGAGCCTGCACTGTGCCGGGAATTGGCACTGGGATTGCAAGCACCGTGGTCATTGCTACCAAGGGAGGCACAGAATCCCTTCACCCCATAGTCACGGGAGCATTGGCAACAAAATTTACCATGCCTTGGCAATTTATGCTGACCCTCACATTTTGGCATTAAAAAAAAGTATCATCTTAGAGTACCAAATATGTGTCTTCTTATAGATGTGTGCCACGGGCCTCGCAGTGAACATCACAGCTCTGCAGATGCACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAA");
        contigsData.add(">contig-2 152 0");
        contigsData.add("TCACATTTTGGCATTAAAAAAAAGTATCATCTTAGAGTACCAAATATGTGTCTTCTTATAGATGTGTGCCACGGGCCTCGCAGTGAACATCACAGCTCTGCAGATGCACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAAGTGGTAG");
        contigsData.add(">contig-13 165 0");
        contigsData.add("GTGTGCCACGGGCCTCGCAGTGAACATCACAGCTCTGCAGATGCACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAAGTGGTAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGATTCATGATAACTATGTG");
        contigsData.add(">contig-0 250 0");
        contigsData.add("CATTCTATAACAGCTATGAAACCATTTGTTATAGCTAATTCGTAGAAGCCAGGCTAAATGAGCCCTAATCAACTAGAACAAGCATTCAGAACTCAAAATCCATTTACATGTTAATTAAGTAAATAGTATAAGCTACACGTTGAGCCTACAACTCTTGCTGGAGTCTCCAGCTGTAAGAGCCTTAACTCCTTCCTTTCATAAAGGCCAAGAAAAGAGTGAGTTAGTCCCTATTCGGGGAGTGTTGGTTGGT");
        contigsData.add(">contig-15 151 0");
        contigsData.add("ACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAAGTGGTAGAATTTTGGATGCGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGATTCATGATAACTATGTGCCCTTATGTGAAACCTAGCAACAATTTCTT");
        contigsData.add(">contig-6 241 0");
        contigsData.add("CTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCACAATCTCCCCAGTCCAGAATCTAAATGGCATTATATTAACATTTTCCTATACCACTAATTAAACATAATGCTACATTCATTTTATTTTCTTCTGCCTCATTCTATAACAGCTATGAAACCATTTGTTATAGCTAATTCGTAGAAGCCAGGCTAAATGAGCCCTAATCAACTAGAACAAGCATTCAGAACTCAAAATC");
        contigsData.add(">contig-10 151 0");
        contigsData.add("TGTGTCTTCTTATAGATGTGTGCCACGGGCCTCGCAGTGAACATCACAGCTCTGCAGATGCACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAAGAGGTAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATG");
        contigsData.add(">contig-17 151 0");
        contigsData.add("TAGAAGCCAGGCTAAATGAGCCCTAATCAACTAGAACAAGCATTCAGAACTCAAAATCCCTTTACATGTTAATTAAGTAAATAGTATAAGCTACACGTTGAGCCTACAACTCTTGCTGGAGTCTCCAGCTGTAAGAGCCTTAACTCCTTCC");
        contigsData.add(">contig-3 195 0");
        contigsData.add("AAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGATTCATGATAACTATGTGCCCTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCACAATCTCCCCAGTCCAGAATCTAAATGGCATTATATTAACATTTTCCTATACCACTAATTAAACATAATGCTACATTCATTTTATTTTC");
        contigsData.add(">contig-4 151 0");
        contigsData.add("CCTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCACAATCTCCCCAGTCCAGAATCTAAATGGCATTATATTAACATTTTCCTATACCACTAATTAAACATAATGCTACATTCATTTTATTTTCTTCTGCCTCATTCTATA");
        contigsData.add(">contig-12 195 0");
        contigsData.add("AAAAAAAGTATCATCTTAGAGTACCAAATATGTGTCTTCTTATAGATGTGTGCCACGGGCCTCGCAGTGAACATCACAGCTCTGCAGATGCACAAACCTTGTCTTTATGAGTCTATATATTAAATAAAAGTGGTAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGA");
        contigsData.add(">contig-8 194 0");
        contigsData.add("CCACTAATTAAACATAATGCTACATTCATTTTATTTTCTTCTGCCTCATTCTATAACAGCTATGAAACCATTTGTTATAGCTAATTCGTAGAAGCCAGGCTAAATGAGCCCTAATCAACTAGAACAAGCATTCAGAACTCAAAATCCATTTACATGTTAATTAAGTAAATAGTATAAGCTACACGTTGAGCCTA");
        contigsData.add(">contig-1 151 0");
        contigsData.add("AGGCACAATCTCCCCAGTCCAGAATCTAAATGGCATTATATTAACATTTTCCTATACCACTAATTAAACATAATGCTACATTCATTTTATTTTCTTCTGTCTCATTCTATAACAGCTATGAAACCATTTGTTATAGCTAATTCGTAGAAGC");
        contigsData.add(">contig-14 151 0");
        contigsData.add("TAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGATTCATGATAACTATGTGCCCTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCACAATCTCCCCAGTCCAGAATCTAAAT");
        contigsData.add(">contig-7 151 0");
        contigsData.add("TATTAAATAAAAGTGGTAGAATTTTGGATGGGTGCAAGTCAAGTAATGGGATAAAAGAATGATGATCGAAGGAGATGACTCATGATAACTAAGTGCCCTTATGTGAAACCTAGCAACAATTTCTTACTTATGATAAAGGCACAATCTCCCC");

        final ContigsCollection contigsCollection = new ContigsCollection(contigsData);

        final List<AlignmentRegion> contigAlignments = contigAligner.alignContigs("1", contigsCollection);
        Assert.assertEquals(contigAlignments.size(), 20);

        final AlignmentRegion alignmentRegion1 = contigAlignments.get(3);
        Assert.assertEquals(alignmentRegion1.contigId, "contig-9");
        Assert.assertEquals(alignmentRegion1.referenceInterval, new SimpleInterval("21", 27373209, 27374158));
        Assert.assertTrue(alignmentRegion1.forwardStrand);
        Assert.assertEquals(alignmentRegion1.mapQual, 60);

        final AlignmentRegion alignmentRegion2 = contigAlignments.get(4);
        Assert.assertEquals(alignmentRegion2.contigId, "contig-9");
        Assert.assertEquals(alignmentRegion2.referenceInterval, new SimpleInterval("21", 27374159, 27374706));
        Assert.assertFalse(alignmentRegion2.forwardStrand);
        Assert.assertEquals(alignmentRegion2.mapQual, 60);

        final AlignmentRegion alignmentRegion3 = contigAlignments.get(5);
        Assert.assertEquals(alignmentRegion3.contigId, "contig-9");
        Assert.assertEquals(alignmentRegion3.referenceInterval, new SimpleInterval("21", 27374701, 27375218));
        Assert.assertTrue(alignmentRegion3.forwardStrand);
        Assert.assertEquals(alignmentRegion3.mapQual, 60);
    }

    @Test
    public void testAlignContigs2() throws Exception {

        // data taken from G94982 NA12878 assembly for breakpoint interval 14591
        final List<String> contigsData = new ArrayList<>();
        contigsData.add(">contig-3 312 0");
        contigsData.add("CCTGTAGATAGAGAGGTGGGTGAGAGATGGCCTTGTGGCAGCTCCTGGCAAGCTCACCTGACTTCTCATGATCTGGGTGACCATGGGGTATCCCTCCAAGACTTAGGTCAGCAGTGGTTAAGCCTTGCCCTGTAGCCTAGGAAAAAATGTGCAAGGTTGTCAGGGCACCAGCATGGAGGAGTTCCCCTACAGTCTTTCCAATACCTATGTGGTCTCTGGAACAGACATTTCATCCAGTAGCCATTCCTTTCCATTGTTTCCCTTCTTGGAAGAGCCTATCTTCCAAGACAGATGGTGAAATATTAGTAATTT");

        final ContigsCollection contigsCollection = new ContigsCollection(contigsData);

        final List<AlignmentRegion> alignmentRegions = contigAligner.alignContigs("1", contigsCollection);
        Assert.assertEquals(alignmentRegions.size(), 2);

        final AlignmentRegion breakpoint1Region1 = alignmentRegions.get(0);
        Assert.assertEquals(breakpoint1Region1.referenceInterval, new SimpleInterval("20", 1388956, 1389146));
        Assert.assertTrue(breakpoint1Region1.forwardStrand);
        Assert.assertEquals(breakpoint1Region1.mapQual, 60);
        Assert.assertEquals(breakpoint1Region1.startInAssembledContig, 1);
        Assert.assertEquals(breakpoint1Region1.endInAssembledContig, 191);

        final AlignmentRegion breakpoint1Region2 = alignmentRegions.get(1);
        Assert.assertEquals(breakpoint1Region2.referenceInterval, new SimpleInterval("20", 1390815, 1390938));
        Assert.assertTrue(breakpoint1Region2.forwardStrand);
        Assert.assertEquals(breakpoint1Region2.mapQual, 60);
        Assert.assertEquals(breakpoint1Region2.startInAssembledContig, 189);
        Assert.assertEquals(breakpoint1Region2.endInAssembledContig, 312);
    }

    @Test
    public void testAlignArtificialBreakpointContig() throws Exception {

        // take DNA sequence from the reference split between 20:
        final List<String> contigsData = new ArrayList<>();
        contigsData.add(">contig-fake-20:1000000-1000099+20:5000002-5000101");
        final String snippet1 = "GTGGGAGAGAACTGGAACAAGAACCCAGTGCTCTTTCTGCTCTACCCACTGACCCATCCTCTCACGCATCATACACCCATACTCCCATCCACCCACCTTC";
        final String snippet2 = "GTGATCCAGCTACAGACTGTTCCAAAGACTTTGCAACTGTTATTTTTGCTTAATCCTCACAACAACCTATGAGGTAGGCACATTTATTGCCCCCATGTGA";
        contigsData.add(snippet1 + snippet2);

        final ContigsCollection contigsCollection = new ContigsCollection(contigsData);

        final List<AlignmentRegion> alignmentRegions = contigAligner.alignContigs("1", contigsCollection);
        Assert.assertEquals(alignmentRegions.size(), 2);

        final AlignmentRegion breakpoint1Region1 = alignmentRegions.get(0);
        Assert.assertEquals(breakpoint1Region1.referenceInterval, new SimpleInterval("20", 1000000, 1000099));
        Assert.assertTrue(breakpoint1Region1.forwardStrand);
        Assert.assertEquals(breakpoint1Region1.mapQual, 60);
        Assert.assertEquals(breakpoint1Region1.startInAssembledContig, 1);
        Assert.assertEquals(breakpoint1Region1.endInAssembledContig, 100);

        final AlignmentRegion breakpoint1Region2 = alignmentRegions.get(1);
        Assert.assertEquals(breakpoint1Region2.referenceInterval, new SimpleInterval("20", 5000002, 5000101));
        Assert.assertTrue(breakpoint1Region2.forwardStrand);
        Assert.assertEquals(breakpoint1Region2.mapQual, 60);
        Assert.assertEquals(breakpoint1Region2.startInAssembledContig, 101);
        Assert.assertEquals(breakpoint1Region2.endInAssembledContig, 200);
    }

    @AfterClass
    public void tearDown() throws Exception {
        BwaMemIndexCache.closeInstances();
    }
}
