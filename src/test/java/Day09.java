import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 {
	public static void main(String[] args) {
		String[] input1 = { "2199943210", "3987894921", "9856789892", "8767896789", "9899965678" };
		String[] input = {
				"9987653234589654549865432101235789979765459832101234567943986543101239874321012932397319985214896543",
				"9898541015678943239876973876545699868954398765212345678932399654592345965432129891985498764323789432",
				"9765432126789654123998765987897789759893219894333656789543498986989957898543498789976579895454678943",
				"9879656737899797434589986798998897646789398989454789898959987899876899987654989678899699976565789656",
				"8998767945678996545678997999569965435678987678967897947898765698765678998799878466798989988676898777",
				"7679898998799987676789398998439876323999876567898976736569894329954569789987964345987878999788999888",
				"5569959349898799787892139987321987549898765456789765623478989497542345699876543239876567898999998999",
				"4398943245999659898993045996210499698759876345678954212349876989321234589876455123954453567899987897",
				"6997832134678945959889159895432349999898763234567894303456965878943445678954321019843212456789976995",
				"9876543265789434345678999789943498899987654126898985424579854567894578789986532129654325677899854789",
				"9987654378894321256789987687894997789976543245789876539798768978965989897898763298767534798998765699",
				"9998867459943210167899976576789876678898674689894988798999899989876999976987654349879545899999876789",
				"8989978967894345234989895434598985568789788799933299987988989999987899895498765767989656987899987890",
				"7679989878965976349876789323987654345699899899321012976567678998998998789329876878998767896789899951",
				"6567895999876765498765678912396543236899956978932129854323567897899989698934998989679878945698789543",
				"4367896989989896989824567893459874345789543569549299765434579945799877567895789997543989432397678954",
				"6456789678998989873213678994598765456795432678998989879765678936998765456789896789732394321234589899",
				"7867894569987678965302759789679876567894321799997878998976789029876744348978945699901298636346698778",
				"8998923459876567973212345698789997678965433899876768987897899998765432124567934989893397545458975667",
				"9769212598765459895463456789894398789877549998765656346789998769876545635688949879799498656867894356",
				"9854324987654346789654577899943239899988698999854341247898987653987678986799198765678999987978965457",
				"9985459876532235678968688987652124989998796598763210123567897632598789987891019654569998798989976768",
				"9876598754321014567999799999873015678999987679874331234898998543469893498989198543456989659497899879",
				"4987999865432323456789899998754123789687798798765765345789239754567912989678976432345678943236910989",
				"3999998978543476567896978899875234896545679969896889656890198765678909876549876321234567894345899894",
				"9899987987654587678965356789986346789639889345987992987999349989999999995434965430999698995456789763",
				"8789995398765698789643235894398956998798999239899893998978969999898989984329876999878989976568997654",
				"7679876219876789896542129901239899889897778998765789879569998998787878965510999876567678987879998795",
				"6569997901989891987843458913498788778976667897654678965456987997676567894323987955476567898989989989",
				"5498789892399932398954567894987654567895456789642567894235695987543478999499876545313456789899876567",
				"6987678789999543459987678959876543679932378996543456965123984399864569998989985432101239898798765456",
				"9876546679898954567999789546987621767891567789654587893239973298765678987879876543212345995697654367",
				"9965434569767895978999893234598832348932345698765698954398764569976789656568987654323476789987743256",
				"8954323698956789899998942195679953567893478999876799965499865678989896543459999866455678999976551045",
				"6561012987545698788987893989998769878987567896987899896989987889298985432198754986566789678985432136",
				"7432123498699987667896789878899989989987679975699989689678998994367965321096563497678994567986544357",
				"6543434679987893459945698766789994597899789654599876575569899765459876432985432398989323456987655456",
				"7654545689876789967897987655679913456789898765989975433456789876767989949874321019998910127898767667",
				"8965656798765667898998986543489894768998999899879874321387899989898998899865432125987891238989898798",
				"9878969899874356789999997432356789899567899999867965434458989999999987678976743434976989349876999899",
				"4989878998543235678989854321247895912456789998759876545789678949899998789987854589865878998785679978",
				"3497989987654146789679765432346954323457991987643987856894589439789769893498767679764567987654598767",
				"2356799998763259895434996566459899496567890987732298997893499998698954902999898798943458976543987656",
				"1345998759998768999323987878698798989698999896521019498902578896587893219899999987654569998651098945",
				"2349887646789878998909998989789657478989298765432329399312456789476789399789999898765878979732129857",
				"3498765434899989987898999999896545365679109876543498989423969893345995987678989769876989569843598767",
				"4599894323987899876667899899987621234568914997656987878944878932239894598547568978989895479955679878",
				"5689989210196998765456789789876210145679323498979766567895989210198795987832456899998794398766789989",
				"6799878923965449887567895698765421234789459989898753456789994345989689976541368954989689239877899999",
				"7899769999874335998789989899876532365678998876789866567899985459878599987430267893976578999988998989",
				"8998958989910126999895678916998645466989897665789877689939876598766469898521256799865456789799987879",
				"9987847768921237899934689105679656567898786544598999994324987987654349765432345987674347889659876567",
				"9876426457892357998925693223998769779987654323987887889212998998743234978545469876544235678998765458",
				"8765212346895468987998789339899878989876543105986455678929899999832123987676569998432123789989896345",
				"9893101287896778956789895498799989294987654219875334599998798987654016898987978987654234599868987456",
				"7654242398969889545678976987689990123499864329883223678998657998762145679398989398766345678956897678",
				"8765357899954995434569989876578921934987986598761019999999736799763234789239799219875458789345999789",
				"9876459987899894323678998875467899895976797987652128789989945689654756894345678998986567891239899899",
				"9987699896798789434789897654323456789985698976543234567968896798765897895656799997897678910198776999",
				"8998988765667678945896789876212345699996789987654345678959789989878998976767899976798989321987665678",
				"7999876554354588996945679877103456789869899898765458789345678969989329987878999865659796499895434789",
				"6789987432143456789439998765213467899953998769896569994234569658991012998989998764546679987654323890",
				"5679896541012345799598789874327878969892987654987678996545678967989129879099876543234567899795434891",
				"4598765432123569898987678985456789459789898743498789989756789879878998765146998732125698901989545789",
				"3759986543234678987656589876769898698657789992109899978997894998767899943237984321016989992978956897",
				"2349898757655989898743456989899999998745678989234998868789923499656798895349875432234678989867897896",
				"3498769898767995679754687894999899899987789878976987654689934987543236789767976543345689876546789965",
				"5957656999898994889898798943499789789998898769897998743476899876432125698978989954496898765435789954",
				"9843545989919989998989899954987679678969987658789876532545899976521034567899999795987999876323567893",
				"8732135678909878967879999899995564599357897645679987621236798765432145678978988689999899943213679954",
				"7643014789998765456568998788994323788969919876798768730345679896543267989569876578998798654323789875",
				"8654123898999654345457897656789515677896323987898654321656789987655358993459875467987698765434892996",
				"8766234567898743212345789534994323456965434598989765442767895698767467892398764345696549876545993989",
				"9874345678999832101234679423989434699799565989679877653458954349878878921987653234986539987656789878",
				"9965657899298743212345894212878945987678976976547989765669765689989989439876543123497698998767899764",
				"9876798921019654323456982104567899866567899867435699878878998799997699545998653234598987899889998943",
				"3989899892198767434569877323456998753467997654526789989989219989876578976789854447679996867995387891",
				"1298956789299878565678965434567897542346789643214567999894329878964347897899965557798765456894216789",
				"0987645898989989678789876545878965321578998759323456999765498767894256798949876678987654345892105899",
				"2698766987678997989898987856989954320789869898937599878978987856943145789432988989998743236789324678",
				"3499989898598965397997698967898765434899754997999987567899876645894235699921299096999984345679434567",
				"9988998765467893236789579989999887545678975986878976458998765434689346789890932125789965456796545678",
				"8976899754359992125679456799999999698789876795467891367999986323478956896799843234699878767899696899",
				"7895789973298989014568967898989439899994987984356910179898797212667897975689754348789989878998789999",
				"6434890989987878923699878987678921999893499878268899298789598601457898954579765678895699989349899678",
				"5423921298756867895789999896569890198789989862156788965678987542356789343467999789934989993210998569",
				"8737893989943458976799998765476789987679876545034567894589999753478993212979878992129878994921987678",
				"9656794979899569987999899654345679876543987632123478943499898764589432109898769994939769789892998989",
				"8767999868767989799898698765476789999432499843234569654986789878997643498789356989898654698789899194",
				"9879898657657895698795439876987899987654678954545678999875688989019759569653239876789543987698789012",
				"6998775432348894239679921997898978998767989876789789789654567899998967898764699865678952986567678929",
				"5987654321256789197598799998929867899898999987899897698973456789787978979978989976789769878454567898",
				"4598763210368898986487678999213456789949989998921954567892567998656899754989978987899879764323678986",
				"3459875391239897697396568789428589890129878999530123678943688974545799843499867898963989875214567895",
				"2345965485446797543212345678937678932398767985432654789764569763234899754998756789642199932108678914",
				"1459879876568999852101267899548789654987659876543465999875678954356999879877645678921019853219989103",
				"2345989987879998763254345678959898767899536987754576789986789765456895998763234599934329764324598912",
				"3589996598989899854395456789967999878998745699865679896597897987587894309854123578896449895434567893",
				"4678965409599798765986577899878986999899656789986789965498986987698963219767012456789567976545998954",
				"5789654312345679876897689910989754346798787891099899876899765698999654397654323767897689987656899865" };
		int[][] matrix = new int[input.length][input[0].length()];
		for (int i = 0; i < input.length; i++) {
			String s = input[i];
			for (int j = 0; j < s.length(); j++) {
				matrix[i][j] = s.charAt(j) - '0';
			}
		}
		int sum = 0;
		//int[][] memo = new int[matrix.length][matrix[0].length];
		List<Integer> sizes = new ArrayList<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (i > 0) {
					if (matrix[i][j] >= matrix[i - 1][j]) {
						continue;
					}
				}
				if (i < matrix.length - 1) {
					if (matrix[i][j] >= matrix[i + 1][j]) {
						continue;
					}
				}
				if (j > 0) {
					if (matrix[i][j] >= matrix[i][j - 1]) {
						continue;
					}
				}
				if (j < matrix[i].length - 1) {
					if (matrix[i][j] >= matrix[i][j + 1]) {
						continue;
					}
				}
				sizes.add(df(matrix, i, j));
			}
		}
		Collections.sort(sizes);
		System.out.println(sizes);
		int prod = 1;
		for (int i = sizes.size() - 1; i >= sizes.size() - 3; i--) {
			prod *= sizes.get(i);
		}
		System.out.println(prod);
	}

	static int df(int[][] matrix, int i, int j) {
		if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || matrix[i][j] > 8) {
			return 0;
		}
		matrix[i][j] += 100;
		return 1 + df(matrix, i + 1, j) + df(matrix, i - 1, j) + df(matrix, i, j + 1)
					+ df(matrix, i, j - 1);
	}
}
