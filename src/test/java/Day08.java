import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 {
	public static void main(String[] args) {
		String[] input0 = { "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe" };
		String[] input1 = { "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
				"edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
				"fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
				"fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
				"aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
				"fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
				"dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
				"bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
				"egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
				"gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce" };

		String[] input = { "ceg gedcfb ec eabfdg gcdabe baged cabgf gbaec fecagdb eacd | efcgbad adfecbg gec abgce",
				"afebcg deac da fgeadc aegbdf fgcda gad dbgfeac cgaef fcdgb | fgace dfgcb cafdg afdcg",
				"gfecb gfabc gdcfeb dcbe efcagd egbafd dfebg ce cef bdacgfe | ce efbdg ec cef",
				"cf cbfag bagfd fcegba egdcab cegab edgabfc acgdef ecbf cfa | ecbag egdfca aebgcd cgbaf",
				"ec cgefab afged fec dbcaef fbdcag bfcag bfdgeca aefcg bcge | ceafg cebg fce cfabge",
				"ab baf dceafb dbfec dagfe bcgdfa efcagdb ebdfcg aecb edfab | cdbagf bfdec ba fab",
				"bcag fgabd cdbegf cfgdb ag gfa edcgafb acbdgf dbefa cdafge | bgfad agf cagb adbgf",
				"gefcab df eafbdgc cfed fad acbdg gebafd dfacb aefcb efdacb | gefdab fd afdcb aecbf",
				"bg fcbda fcbgd bcafde bdeacg dbgfac bagf cbdegaf dbg dfecg | bdfeca bagf bfdaegc bdgefca",
				"fcgebd efcga cfeab cg cbga febcag beadfc defga gcdebfa gfc | eafcg aedfbc febcga geacf",
				"beagcf decga bgdc acdfe efdgba dge agcbde gd bcfeagd cebga | gdacbe gd fegbda adfbeg",
				"ed cbagdf gcfea aecdgb dae fegdab fdbe edagfcb edgfa fgbda | gcadeb aecbgdf bgcdaf egdfa",
				"cgdb afbedg agfbcd ecabf gc cadefg gcbfa cgf dcegfab fbdag | fcg acgbdf cgf cg",
				"edacg af bafedgc fdegba bfecdg gaf fbac fcegb ecfga faegbc | af edagfb fa agf",
				"ac fbdea dbagfe fecdg dfabce bcfdgae aceb acedf gcfbda cda | ca eacdf feadbc bcea",
				"acdebf bdafgc de edgba deabgf gbeca fabdg ade fged egabdcf | gdaeb fgadb dfge gfcebda",
				"bc dacbeg dcage eacbd gedcaf afegbc bgcd abcedfg acb dbfae | dgace afdgec edafb bgecfa",
				"fbged gc gce facbed bacefgd cedab adcg eagcbd gdebc afcgbe | gfceab gc gec gc",
				"aefcbgd adbgc ad afcgb degbfa dfac gda cebgd gfbeca gbdfca | gebcd edgabcf cafd ad",
				"gcbed cef egcdba gfdc fc gfbced eafcdbg acefdb fegcb gaefb | bdagec fdcgeb cef gbcef",
				"dcabg ecabfd ecdbgfa bdfac fbagec ga agc afdg bgdce dbgafc | ecadfgb agcdbf gac cga",
				"beadcgf fcead ef bfdac egbcaf dagcfe edgf acgde geadbc aef | fcebgad cegfba fcabd afe",
				"fadgbc fabdce acbgdfe cdaeg ecf faecg gecbaf fcbga ef bgef | fbeg ebcagf eacfbg fec",
				"gefcad cg bacg fcegb eagfb dbfec fdgbae gfc afcgedb acefbg | bafdge dcfaebg cg afgceb",
				"bafedg cebgf cdb cd abcgde acdf badgf cdgbf dacgfb dabgfec | dfcgeab gfdcb dc dagcbe",
				"da cgedfb cbdgea bcafgde gbda ecfba cdeab edcagf ead bcegd | beafc efdcgb bdecg ecbdga",
				"febdc gf eagf fdecgba dgcea adfgbc gecadb cadgfe gcf gfced | adgce gdcabe efga gfc",
				"bfaegd begdf afcbgd bdg afbeg edgfc fegbacd feabcg abed bd | gabdef agfdeb afgceb bfaeg",
				"dfa agde edgfc gcefda cdbfea fdgca egfbdc afcgb fgcbade da | gfdeac fagbc efgdc dfa",
				"dbgfe bcdge faedbg fgcebd ecdbfa dbc becfagd gcbea cd gdcf | egcdb dfgaceb fbegdc fgbde",
				"agbfc fgebcd da fceadg gbcaefd edca dgfaeb fagcd gad efgcd | bcfag adg gcbfa edafcg",
				"afgdbe bc fceb gbdafec dacbef bca cfabd dcagf bdafe cebgad | efdba facgd fcdag dbfea",
				"agcdb cge faegdc eacbg eacfb bcafge egacfdb begf ge acbefd | aebfdc bfdaec cabge beacf",
				"gadbf fgbdea dage egbafc fcedbag bad gdcbf dbfcae bfgea da | dgbeaf bdagfe ebadcf cgdfb",
				"bdaefc abecgd bgefd cdga bcafge becdg bgeac dc bcd feagbdc | facdeb ebcag dbc gbedc",
				"fd fgdb adf bfdecga gdeca fadegb eagfd fcbead abfeg afcgbe | bdgf gbdf acbdef egdaf",
				"bfgaced ebgf aebgc fbaegc ecadf cagedb afceb bf bfc gfadbc | eacdf bagcef fbc adcfe",
				"fce cedabg cfgde efgcdb bdgceaf ef adgcf fbge bcged adbcef | caedbgf dcgaeb aefcdb cef",
				"dacfe ecbad cef bcfa cdafeb aecgbd gedfa cf gebdfc aecdgbf | ecbfdg bcfa debcfa cf",
				"gbdcfa cbgfd cedab eg ecdbg bge gceafb dcgefb gdef cfgbdea | adbec gedbc dbacfg decbg",
				"cfdebga adb cebag dceb cafdbg bdeag eagcfb gdacbe afedg db | cebd fadge eagdcb bad",
				"efc fdcab bfae dfgbca bacfgde dbceaf bfgcde gecad ceafd fe | abdfc cfbda cdefa dbafec",
				"cdfg efadb fabcge cagedb fgb fg gfdba edbgfca acdbg bgdacf | bdeaf abgdc fbacdeg becdfga",
				"fbcgd ecfga afcbg gcbeafd agfebd aecb fab gfaecd ba fbaegc | fcbga afb cfbag abedfg",
				"beaf gdbfc geadfc gefda cbdage edbcafg gba gdfab ba egbadf | dbgceaf begcda bagdec feadg",
				"fbagec egafdbc dbagec adgefb gadc dba aecbd edfbc ad eabgc | cdaegb ad eabdc da",
				"gbdcea dgbca fc cafg fgbed dfc egdbacf gfbcd fdgcba fbdeac | dgfabc afgc abcdg aebdfc",
				"gbc cefbgd aegcfdb abdfeg cgedb dcgf cg bcade gcfeba edfbg | cbgefd abgfced dabgfe dcgeb",
				"bcefdg dcfeag fdaec edcfg dae badgec gadf ad feacb ebcfagd | fedac fdgce gcafed gfdecb",
				"bgacfe bc becfa bfc ebgc fgecda fgedbac bcfagd fgeac dabef | cbf gafbec febca ecbg",
				"bfeca cadfbe afdbceg dcgaeb acefg bce faebd fbaegd bc bdfc | efdab cbe cfbd fadcgeb",
				"fgbcd ed gcdbe fbde eagcb deg dcbgfe gbafdc ecbfdga agcdef | cdbfg cbgdf ged defb",
				"gefda gb egb fceba efbcgd cdfaeg dgab dafbgce afbeg beadfg | ebfac fdgcea cdegaf decgafb",
				"acedbf decaf eadbgcf adgfb fge egcfad fdgea efbgdc eg aegc | afedcg dfbeca dfegac adbfg",
				"ecdfg fgba aebgdc ega begafc cbafe abdfegc acgfe adcbfe ag | fcdagbe afcbe ga bgfa",
				"afbec dafceb fdcgeb gef ebgcafd gcfa dbgea geabf fgecba fg | dcfabe feg cfeba febadgc",
				"edbfag fceagd bfcd dc abgdcfe abfdg fcdbga egabc cgd dgbac | acbge dc cd dcg",
				"bdaf bfcegda adg agfecd fgcba fgdacb da cfgeab cbegd cdagb | cgfab dga da ad",
				"dgfcb abgedc agcfe cfedg bdef dge gcdfab egcbfd bdfagce de | gde cafeg edg dbgfac",
				"dg fgbcde bdgaf cfgeabd adbcf aged ecabgf fdabge fgd gbfae | aged gd agdefb gdafb",
				"ab bgca dabge cdfeab gdceaf cagfdeb gecda cdeabg gdebf bea | dbafce fecbad fedgb bcefad",
				"gbfda gacebd gcebf gabfecd cfbaeg eabgf fcae eba fgbcde ea | gcebf ea ecbgafd ebcfga",
				"gecdf febgc fbc afbgde befgdac fbgcda bc bafgec cabe fegab | bc cefgb bagef fgaeb",
				"afcebg fe abedfc fae egbf cgefa abecfdg cbfga fcdbga cgeda | cabfgd acedg cfgdba bgfcad",
				"eagdbf defcg fcgb gebcfd gf gef cedbf caefgdb cafbed gadce | gef gf cafgdbe dcgbef",
				"bfac bdcgae gbefacd ace efadb ac edfac abfdec adgebf dcgfe | dbgfea dcebag abdef ca",
				"eabg dagfbe eagdf gcafdb afgbd feacbd efcgdab ae fcdeg fae | fegad dfbacg eacdfb fgadb",
				"cgeabdf ce bcdafe gecf dabeg aedcfg bdcgfa ecd fadcg egacd | edcafg cefg gbcfad cfeg",
				"cfdba geabdcf cf abgdc abefd gbceda gafc fcb gdcbfe gcfdba | cbfgda gfdacb cafg fdabe",
				"gcbfe faed fbagdc de adfgc agcedbf bcgaed dfecag gde cdgfe | edcgfa cadfge cadfbg cgbdea",
				"edcbg fedacg acdbg bcefag cdgfe be cefdagb bdef bcdefg ecb | fbeagc eb dfegc badgfce",
				"bfagd abcgfd debfcg af gadecfb abf cdaf dgcbf eabdg faegcb | gacbef fbdgc fa dfcabg",
				"deabfgc cedfbg eafbc cafged efdabc cfdea fagbc be bead cbe | gbfac dbgefc ebcgdf gbafc",
				"bgfad cfabg bcea cfegad cgafe fdbceg cfb ecfbag cb dfgebac | cefagdb cb gceafd bcf",
				"afg egbfdc cegafb gcdea cbefdag begcf bgeadf cfab af ceagf | dgebaf fcab dagbfce bfac",
				"de ged bdfecga adefgb ebgfd bcdfga fcegad baed fgdab gfebc | aecfdg ebgfd ebdfg fdbaecg",
				"fdbcgae badgc bfcg cadeb edcgfa adbgef abg gacfbd gb agdfc | gb fegbad fecagd abdfgc",
				"egfa agd febadc efbad fgdcb bafdg acdefbg egdabf bgcaed ga | gfdbc ebafgd bgfdae fgcdb",
				"gbfade egcbfd gadf bfcae ebfgd ad egbcad dafbe bgdafec dba | fgda gfdbe cbedgf adbef",
				"af gcdba fabdeg egbfd gcedbf fbeacg bgafd dfea cdaegfb gfa | dgcba fga fbdga gbfed",
				"eadc cdfag gfedb cfebag gcdfab gacefd gae gdcbfae ae egadf | gacdfe dfcag gbacfd ea",
				"dcafb bfedc ed bed dgafcbe baecdf cbgef deca bgafde bgacdf | faegdb dgcfabe fdgbca fcdeb",
				"afbgd cbaefg fedg bcdae bdaeg adfgbe ega bfgdac ge dfbgeca | eg aeg gae acgdfb",
				"gfedb fdaegc bacfegd dc defgc fgeca edc ebcfga dgebac facd | ecfbga cd fbgaec edc",
				"ecafd abe fegcb efgcab fcdebg ceabf aebdgf cfedagb bgac ab | eab cgfedb fcabeg fabedgc",
				"febad edgfc bdgc gb bcdagfe geacdf edbfcg agbfec fbg dbgef | ebdgf defbg fbaed gaefcb",
				"abfe gafcde dfegcab cbefg agf gcafb ecfdbg acbgd agebfc af | dcbgef fcadbge fag bgacd",
				"fa cafedg aegdc becfag aedf fag gfbcd ebcgda aecbgdf cgdfa | af bgcdf acegd dbfacge",
				"agdcef debac eca bafdc ea dabgecf aefb fbgacd dbgce eabcfd | bgcdaf ae feacdg dgbce",
				"ad cedfg bfadeg edafbc efagd gdbfcae fgbae fgebca adf bdag | geabdfc agfebc defcab daf",
				"abedfc fabged bfecga gea acfgbed gfcad gdefa ge ebfad egdb | acfegbd gbcafe degb faecgb",
				"dgaef dgfcb eabfcgd dcefg bdegcf ecg fbcadg cbgafe ecbd ec | acbfeg cgfde ce gedcfb",
				"cdfagb aecgdbf cfbdg ecafgd afbge dcba ad bgfdce dga dgafb | ebgfa ecgfdb bdca fedcbga",
				"ecbdagf egacb dfcabg dbge acbegf adb dbgaec dafec cbade bd | dcbea egfcba abefcg db",
				"fecgda acgeb bd bdcf abecd fgabde fecda bde edbfca fbadceg | edb badce cabge edb",
				"dabfc ed dabefc gfcebd ebda cdefa cagfe cgfedba bcfdga dfe | facdb bdefcg cdbfea afbgdec",
				"bgfced fgbac fdag fa cdabfg fgbcd ecfbad fac fgecbad gcbea | fgda cbfdae fgdbc dagcbf",
				"gcd fgeda dafc gabcefd ecbdfg baefdg dc cfgade adcge ceagb | cbdfeg cbgea cd fdgaec",
				"gbcaefd fdcag gcefd ed dfeb ecbfg febcgd abgedc dce bgaefc | aegcbd afcbeg gcefb gfbce",
				"debag cgaeb cag bfgdae abfce dgcb cgfdae bedagcf cabegd gc | gcadef abgde abgedc fagedb",
				"fedgcb dagec cbagdf acebd adegcf afeg eg cdefgab gde fdgac | dcgaf bcafdg geadc fage",
				"ebgdca eafgc cb abgec gbcfed gbc bcfaegd dgaebf bdeag cbda | cdab aedcbg bgc aegcdb",
				"cdgbfe bcfgade bdfce af dafe cbgae fba bcdfga ecabf abecdf | eabfdc afed gdacfb defa",
				"aegcb bcdf gcefad cdbea dce ebafdc gedbfa fdbea cd cbedfga | ced cde dc bfdeca",
				"gadcfe dfgeb bcgade agefbc bedag bdca aeb efgdbca ba gdeca | cbgfea eba bcgdea gdaecf",
				"cgfebd agdefcb cfdea agcfde fecag fdag efcbda feg gf bcega | cdgabfe acfgde gfeac decafbg",
				"gbefdca cbfgae gfeda fa bfad aef fabedg dgcea cbfgde bgdef | bacegf daecg aegfd edgfa",
				"aedgf bfadcg edfcg cedfba fbecgd agbfedc gcbe dfebc gfc gc | cgeb dfecg fcg cg",
				"gefdabc eb bea febd gedba gdfae gfaecd agdebf gbacfe cagdb | eba eab bcgfead agfde",
				"caegfb dace gad bdegfac bagecd dbega gbaec fagdcb bedfg da | edagbc eacgb gcadfb fgbcae",
				"caeg fge agfbd adgebfc aedfc dcgbef daefg ge fadecg bfedca | bafedc fdbga eacgfd gcefdb",
				"dfb ebcfad adcb bfgae gafdec acgfbde db eafdb cgfdeb fedac | egbdfc fedab edacbf cbgfde",
				"ac acfdge eacbg cfgbe efadbcg eca cadb edbga eadfgb badgce | cbad dacb gcbef fbeagd",
				"ecabdgf dfgbca fadbc ae afe bgfaec abdfce gfbde efdba aedc | bgfde dabfgc dgefb cbafdg",
				"cfgba decfg efcagdb dgaefc adg debgaf ad gdacf bfdegc ceda | dfagce gcfdbe fbegad adec",
				"gbcaef bgd cabedg dg fdecb dcbegfa cgda eagcb ecgdb bgfeda | edcgab bgdcae begacd cgadbe",
				"fgedbc bacged cfged bdcef ge fcagd egc edgabcf fabecd gebf | bcefda cfegd bfeg cfdeba",
				"ecgfa dcbfea eb fcbda dcgfab febcdg bfe cfeab ebad fcdabge | adbe eabd abfced gfdcaeb",
				"bcafg cafge cfgeba bcea ec dbceagf cef fgbedc dgefa dfcbag | cfabg dfaeg cafge aefdg",
				"dcegaf cefg eabdc efbadg dcbaegf egd ge cgead fcdabg fdagc | cfgda bfgadc fecg dacbe",
				"cgedb afdebg acfe baedc dfgacb fabcde ca cba acbdfge daebf | acedb dacfgb gdecb acbed",
				"fgcdeb dacb abfge dfacge debfc ecbadf bfdaecg dfa da dbafe | bacd cdba adefgc dfecga",
				"fbadg gbfca cab cdfaeg fdbeac cb cagfe edgabfc cegb gaebfc | gfbda adfgb adgcef gcefa",
				"gcefa eafbdgc ca eacgbd bfgea bcaf bgdaef acg cfgabe dgecf | facge ecbagf aecfg beacfdg",
				"baegfd cdgfeb gbead befdg geadbcf egfabc afdg gea ga aecbd | dfag efdbg afbegd facbeg",
				"afbec fagedc deba cdaef cdfebag acb bdfcea ab fgebc fcbadg | bcgefad ecgbf dacfbg ecdfa",
				"dagecf gdeabc cd fdbge cfbaeg cde fbadgce cafd cefgd gacfe | dec gefcd agdcbe cdefg",
				"acbeg afecb cfaegb bfacde dcbagef cg gacf dbefgc aedbg cbg | decgfb dgcbefa efabcg dfacbe",
				"de edg fdeb gafdb fgeca gdfeacb cfgabd badecg adgef fdgaeb | cbgade de bafgde efbd",
				"dcf gefad bedcfga fgaedb dfcae dfbegc cfga cadbe fc afgcde | ecadb fdcae cfd gecafd",
				"agecd gad cgfd abdegf bfdcae ceabg dg cgdfea cgadfeb adcef | fcdg dga aegbc afecdb",
				"bg dfage bcga ecdbfga dbcafe gdcbfe aebcd bdgea egb dacebg | dgcfbe ebgad bacg dbgefc",
				"afgcd acdfbg fdegabc gbdef bc dcafge abcd cbgafe dgcfb cfb | bfc cbfdag bfgde cbf",
				"cbdeagf egcaf ebgca bgcad cdgaef ebfg aecbfd ecb eb bceafg | eagcf fdceagb aebcdfg cfega",
				"cfbae efcad aed dgbeac ecfgda dcgef dbcgef dgceabf ad dgfa | eda fdegc baecdg agdf",
				"gdcabf gbda fecbd cefagb gfadc abdcefg dfgbc gb afcegd fbg | cfdega cfgbea fdecgab bg",
				"adfgbe egfb dabgcf gbcdfae abfgd eg ebcdga gdfae gea eadfc | egbadc gae gdfba bdcgaef",
				"ebdcga aefbg gf dabfge bcdgef bfg fadcgeb afdg aedgb fcbea | fgb bagde bdecga ebfag",
				"bdage gfbead fga fbagd beaf af aecdfg abgecd fcbgd dbacgef | bgafd bdgcae eagdcb afeb",
				"cfdaegb dacfe cbd acgfed gbcefd dfba cbeda abcfed db cgbea | bdcae dabf aefgdc bd",
				"dfgca dfaecbg cgedaf dcbgaf bg efabd daegbc gcbf gdbfa dgb | faebd gedacb fbgcad efdab",
				"db bgafce afbdcg bdgf dbgac deacg eadgcfb bdc gbcfa fbdeac | agfbcd cbdgaf agdfcb cbd",
				"dgbef egdfac gb agbf faegd badecfg ebg bdeagc ecdfb adgfbe | gfedb gfaebd gbe beg",
				"dcgbe gdac cbaedf decabg gec gedbfca aebcd bgfed cg fecabg | bdgeca dbgec agcbfe ecbdg",
				"cgdfbe fa abefd dbcef fcaedb fda aecf dfagceb dgbcfa eadbg | afbced fbcde af bcdaegf",
				"abegdc agfb efbcd gdcebfa gf acdefg gdfbe abdeg feg dagefb | gef bacdeg egf gfba",
				"cdeag fd bacgefd fedb fgd gfdebc cgfeab dcgef dgfabc gbfec | cdbgfe bfecg bgdfca bfgcde",
				"agbde gc bacfdg abegdc cgade cga ecgb edcabfg ebagdf cdefa | gbfacd gc cgeb fagedb",
				"cgbade fage ae agbfc eacbf dcefb egbcaf bcafdg fbecdga bae | ae eacbf ceabf eacgbf",
				"egb cfeba agfdb ebdcfg afbge fgacdb ebdfag eadg ge fbeagcd | abgef edfbcg eg ge",
				"cgdbefa bfeac bgcd bdagef fbcdga cbdaf cfedga bd fadcg fdb | febca dfeabcg cfeab fcabe",
				"gdbeca gfadcb ebfag cdaeb cgaedbf edgc bagec bcg cg aefcdb | cdeg eagdcb abfcgd degc",
				"fabegdc ba debfgc bfea cdeag ebagd gbdefa gba bdfge dagfbc | fdebag dfbegc ebafdg fgadbe",
				"dcaef dgcabf abced eabgfdc gbec gbedca abe gdcba be eagbdf | abdgc cbadge cgdfab abe",
				"efcadb fbdae fadbceg efbdga gdebc fecgad fecdb cf dcf facb | cdbeg cdbge dfc efgdba",
				"fcbad cae ea gdcabf gbfeac eafdc efdbgac cfegd befcda aedb | edabgfc gfecd acfegb cae",
				"gdbce efac acfgbde bac bfdage ca abdec bedaf bfcagd fbedac | ac acdbe ac adbgfec",
				"acfbged bgcdfa bdgcfe eagfd gacdeb ec ceba cadeg cbdga ced | ce aecdgb acdeg efdga",
				"afecb afcbd dgbc cdafg bdafeg edfbgac afgdec fbd bdagcf bd | bfd dgbc db bfd",
				"fgb dbgeac gbfdea egcba fbaecg faecbdg fb bcfe fcbga cdgaf | bcaeg bgaefd bf bfcga",
				"gcfb aefdb fgecda gbdce dgcbef dfebacg edbgca cf fec bdfec | efbad dbeaf cdgafe efcagd",
				"agedb eadfb fea cafbde egfcba fdec agbcdf fe cfbda bcefagd | bfeagc gbaed bacegfd dafbe",
				"ae adfbg bcagfd gafbed dgbae aecfgb age fegcbad deaf edgbc | egdab agbecf gbaecfd cgebd",
				"edbf bf gbf abdecfg bdgca fbgeac bdgfae dgcaef fdeag dfagb | bcgad egadf fb agbfed",
				"cgafd ge fgeacd dgbacf fadcegb aefbd gcef deagf dagecb dge | egd cfge efabd gde",
				"cbfadg gfdac defcag gcfb badce edabfg dgb gbdac gb dbceagf | adebcfg gdebcfa faegcbd bfgc",
				"aed abfgd eafbc fgde adbfcg badfe de dfcabeg debcga defagb | dbfae dbagfc acbef fgdabe",
				"degaf gdeafbc aedgfb ab bda eabdf caedfg fabg cgdeab efdcb | bad bgaf gdbaef abd",
				"dcfae aecg cg gcd cegfd fdgebca daefcg afcgdb fgbed cfeadb | gdc dbcafge bgfdace fbedg",
				"fabec dabefcg cbde bedaf adecfb de adbfg dea ebfcag fagdce | abedf gadcef dceb cbde",
				"dfcbg aegdcf eg ceadf cgfde cbdgea gadefcb egc bdface afge | efga geaf edgabc ge",
				"gafdce dgabcfe egbda cb bcd dfbgce fgecd cebf bgdec fcdgab | begcd eabgd dgfec dbgfce",
				"gfacebd feagd gdbafe bfed acebg dcgafe bf bgfea fab fcbadg | dfeb egdcaf aegdcf fegad",
				"ca dgcbfa cda afegbdc baced fabedc bgadef cbdeg adebf faec | gefdba cdgfab fdebca afgbdce",
				"dgebc eabgc bgaedc cae dgea bdgeafc ecfadb ea cfbgde fgbac | bfacg ebagc egacdb faecbd",
				"abed cafdgb ebg geafdb bdfga ebfag dfbgce beacfdg afgec be | bfgeda egbaf ebg bfage",
				"gbeacd feagdc bdcge cef cbefgad dfbc begfcd egbcf fc gebfa | gebfdca acdefg fegdcb gbdeac",
				"afdecg gcaf adg eadbcg fbdeg cebdfa daefg dcafe ag fbaedgc | fgbde facg dga ga",
				"be fdagb agbed abgedc dceb adgec efcbdag fdceag bge abegcf | bfgeac eb bafdg cbed",
				"begcdf bafgec deac cgdba gcbde ca gfcdeab cab gbfda edgbca | beagdc eagcbf agcedbf bdgac",
				"egdac bgcedf cdgafb bedgaf bgced bc defbg bcg fcbe cgfbdea | bc cbfgde gabfedc bcg",
				"dbecg acbd ca becgdf ace bfgcae afedg gcdefba cdage adcbge | caegbd cbgfdea deacg geafd",
				"afgecdb cadbf cdgba egcad gdcafe gebd geabcd bg cgb ceafgb | dbeg eacfgdb cfaegb cgbda",
				"ace cbag abedf acgdfe afgecbd bdfcge gfbec acbgef cfeab ca | cbedgf cdbfeg dfeacbg fbaed",
				"ecdbf geadbcf dc ecafbd gafbce gbfed bdc cfad bgcade cfabe | dfebc dcb acfgeb gbfeca",
				"gae aecbf gdbafec cdag egbdfc gdfec gbaefd cegfa ga fecdga | fdbgea ag dcgef gfedc",
				"egda efa dcgafb dacbfe gfcda ea fcebg cgaef facdbge edcafg | abdcfg fdegcab eagcfdb fcabdge",
				"gfdeca fegca ebadfc adgcf fgde dgfaecb fda abgefc agcbd fd | efdg daf dfcage ecfgbad",
				"acedfg dbcage edagbcf aefcb dfceab fbed dbaec abfcg fea ef | edafcg fae bcgdae acedb",
				"gebafc cdfb gdefab db bdgfec gebcf gdcebfa cbged cdeag bgd | gbd fegcdb bd gafbed",
				"cagbdf adbfc edgfb bgafecd dafgec cbaedf cbae efa ae eabdf | fcdega gaecfd cadfb acbfd",
				"aegbf bedagf aecgf dbgf dgbeac becfad badfe gb dbaefcg gab | gfbd bdcfae gbacedf ecgaf",
				"db gdb bcgafe fdega gedbac bgdfcae befgc gcbfed dgbfe fcbd | ebfgac bdcf gadef bgd",
				"dcf dgcbe dcfge feda bgfacd df fegdca gfeca cbegfad gfaebc | fgbcda bcdge dfc gcefa",
				"decagb gfdcb abfegc dfbaegc fca dgacf defa fcgdea fa edgca | bdaceg ecgad bagecf edaf",
				"gefabc abegc facebgd adgcb dag egcd gd dacbf acgbed dabgfe | cgde eagbc dfbgae agcbd",
				"cadfb dfebcg gdfbc cgb fegb fadceg gb bgacde fcgedab ecfdg | bgefcd fgcaedb degfcb fcdbg",
				"gabcf fdbac aefgb bfdeag gecafdb geca gbc cfgabe gcefdb cg | cgbaf feabdg geafb gfbea",
				"adefg geafdcb bgd gbcfe fdbeg bd bgdcae bgcefa fegdcb cfdb | cfdb bd dfbc abfceg",
				"aed aecfbdg geafc cdeaf efdcb bcgedf bdaf da gbdcae beacfd | ecafd ade cdfae bedcf" };

		int sum = 0;
		for (String s : input) {
			String[] parts = s.split(" \\| ");
			String[] digits = parts[1].split(" ");
			Map<String, Integer> map = helper(parts[0].split(" "));
			int k = 0, num = 0;
			for (int i = digits.length - 1; i >= 0; i--) {
				num += ((int) (map.get(sort(digits[i])) * Math.pow(10, k++)));
			}
			sum += num;
		}
		System.out.println(sum);
	}

	static Map<String, Integer> helper(String[] digits) {
		Map<String, Integer> toDigit = new HashMap<>();
		Map<Integer, String> fromDigit = new HashMap<>();
		int[] fourSeven = new int[26];
		Set<String> len6 = new HashSet<>();
		Set<String> len5 = new HashSet<>();
		for (int i = 0; i < digits.length; i++) {
			String digit = digits[i] = sort(digits[i]);
			if (digit.length() == 2) {
				toDigit.put(digit, 1);
				fromDigit.put(1, digit);
			} else if (digits[i].length() == 3) {
				toDigit.put(digit, 7);
				for (int j = 0; j < digit.length(); j++) {
					fourSeven[digit.charAt(j) - 'a'] = 1;
				}
			} else if (digit.length() == 4) {
				toDigit.put(digit, 4);
				for (int j = 0; j < digit.length(); j++) {
					fourSeven[digit.charAt(j) - 'a'] = 1;
				}
			} else if (digit.length() == 7) {
				toDigit.put(digit, 8);
				fromDigit.put(8, digit);
			} else if (digit.length() == 6) {
				len6.add(digit);
			} else if (digit.length() == 5) {
				len5.add(digit);
			}
		}

		Iterator<String> it = len6.iterator();
		while (it.hasNext()) {
			String t = it.next();
			if (distance(fourSeven, toIntArray(t))[0] == 1) {
				toDigit.put(t, 9);
				fromDigit.put(9, t);
				it.remove();
				break;
			}
		}
		it = len6.iterator();
		while (it.hasNext()) {
			String t = it.next();
			if (t.indexOf(fromDigit.get(1).substring(0, 1)) > -1 && t.indexOf(fromDigit.get(1).substring(1, 2)) > -1) {
				toDigit.put(t, 0);
				fromDigit.put(0, t);
				it.remove();
				break;
			}
		}
		for (String x : len6) {
			toDigit.put(x, 6);
			fromDigit.put(6, x);
		}
		char segment6 = (char) (distance(toIntArray(fromDigit.get(8)), toIntArray(fromDigit.get(9)))[1] + 'a');
		it = len5.iterator();
		while (it.hasNext()) {
			String t = it.next();
			if (t.indexOf(String.valueOf(segment6)) > -1) {
				toDigit.put(t, 2);
				fromDigit.put(2, t);
				it.remove();
			} else if (t.indexOf(fromDigit.get(1).substring(0, 1)) > -1
					&& t.indexOf(fromDigit.get(1).substring(1, 2)) > -1) {
				toDigit.put(t, 3);
				fromDigit.put(3, t);
				it.remove();
			}
		}
		for (String x : len5) {
			toDigit.put(x, 5);
			fromDigit.put(5, x);
		}
		return toDigit;
	}

	static int[] distance(int[] s1, int[] s2) {
		int[] ans = new int[2];
		for (int i = 0; i < 26; i++) {
			if (s2[i] != s1[i]) {
				ans[0]++;
				ans[1] = i;
			}
		}
		return ans;
	}

	static String sort(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

	static int[] toIntArray(String s) {
		int[] ans = new int[26];
		for (int i = 0; i < s.length(); i++) {
			ans[s.charAt(i) - 'a'] = 1;
		}
		return ans;
	}

}