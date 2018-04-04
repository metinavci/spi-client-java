package com.assemblypayments.acmepos;

import com.assemblypayments.spi.util.DiffieHellman;
import com.assemblypayments.spi.util.RandomHelper;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiffieHellmanTest {

    @Test
    public void test_exchange() {
        BigInteger primeP = new BigInteger(
                "32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");
        BigInteger primeG = BigInteger.valueOf(2);

        BigInteger privateKeyA = DiffieHellman.randomPrivateKey(primeP);
        BigInteger privateKeyB = DiffieHellman.randomPrivateKey(primeP);

        BigInteger publicKeyA = DiffieHellman.publicKey(primeP, primeG, privateKeyA);
        BigInteger publicKeyB = DiffieHellman.publicKey(primeP, primeG, privateKeyB);

        BigInteger secretA = DiffieHellman.secret(primeP, publicKeyB, privateKeyA);
        BigInteger secretB = DiffieHellman.secret(primeP, publicKeyA, privateKeyB);

        assertEquals(secretB, secretA);
    }

    @Test
    public void test_against_well_known_results() {
        BigInteger primeP = new BigInteger(
                "32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");
        BigInteger primeG = BigInteger.valueOf(2);

        // Well known inputs
        BigInteger publicKeyA = new BigInteger(
                "16428453901689311174406835429457614352149131480820814606112641749162650046154160335721379864411385528632019438902509157814200706062493374586063287172414190665332638992378261617325351180882041320307470593551646072474377878641984257780799839924855832129077529540835293783943730689922011709290843103151179985344516127747230985665149485674718135070425535512704941196438784396460314598697418809403784160603971605277453951697026605904451672993162168584425019633974228954913888533522579571876177351844336213083376100007173872157215792163324914952633720486776246713336194027578118386085155631307325675034868183575746215669017");
        BigInteger privateKeyB = new BigInteger(
                "6613069657665132725246464674416626967814847789270511732153920138404502558419161452231064033783065518081120087482262648699449248725844190853405394370415215555753121974020933054263318571431735489635061130413831465735285677393247377148888841381226497879709898084741908757014541741029147966946395707074610452892001890884190368089685289931823630320756921252041961571241320436492694638582502471480887058785121023850135911685185196576270777539716337738304019266982029836194150504415828024197423759537800908924209753175640206646764556125280475452980539359806836287847410461780237281850073248139315083092664821627343007413317");

        // Calculate Public Key
        BigInteger publicKeyB = DiffieHellman.publicKey(primeP, primeG, privateKeyB);

        // Should result in the following well known number
        assertEquals(publicKeyB,
                new BigInteger(
                        "13937492206031047726377677746757921023785865161711475317174852743458085972205230299198350504612922098487548140816754440307076166055257752333977500237623761651798309657163956423196446289209492990021148645911047877880418288215506315988031313308757685574136931332211416208698474521512055282419261914878127800815451795657625776361486208624055307330917667568520525506193865130553366132859605894136270581379193553757875072525875034653743007926569724507481311440697029586510437642238099656004667926545975053817946452156676950868454136633495486741131821585053777971482962860700849249057853674609970118970647438173499487768514"));

        // Calculate Secret
        BigInteger secretB = DiffieHellman.secret(primeP, publicKeyA, privateKeyB);
        // Should Result in the following well known secret.
        assertEquals(secretB,
                new BigInteger(
                        "17574532284595554228770542578145458081719781058045063175688772743423924399411406200223997425795977226735712284391179978852253613346926080761628802664085045531796220784085311215093471160914442692274980632286568900367895454304533334450617380428362254473222831478193415222881689923861172428575632214297967550826460508634891791127942687630353829719246724903147169063379750256523005309264102997944008112551383251560153285483075803832550164760264165682355751637761390244202226339540318827287797180863284173748514677579269180126947721499144727772986832223499738071139796968492815538042908414723947769999062186130240163854083"));
    }

    @Test
    public void test_secret_against_another_well_known_results() {
        BigInteger primeP = new BigInteger(
                "120227323036150778550155526710966921740030662694578947298423549235265759593711587341037426347114541533006628856300552706996143592240453345642869233562886752930249953227657883929905072620233073626594386072962776144691433658814261874113232461749035425712805067202910389407991986070558964461330091797026762932543");

        BigInteger publicKey = new BigInteger(
                "75205441154357919442925546169208711235485855904969178206313309299205868312399046149367516336607966149689640419216591714331722664409474612463910928128055994157922930443733535659848264364106037925315974095321112757711756912144137705613776063541350548911512715512539186192176020596861210448363099541947258202188");
        BigInteger privateKey =
                new BigInteger(
                        "2483479393625932939911081304356888505153797135447327501792696199190469015215177630758617902200417377685436170904594686456961202706692908603181062371925882");

        BigInteger expectedSecret = new BigInteger(
                "70900735223964890815905879227737819348808518698920446491346508980461201746567735331455825644429877946556431095820785835497384849778344216981228226252639932672153547963980483673419756271345828771971984887453014488572245819864454136618980914729839523581263886740821363010486083940557620831348661126601106717071");
        BigInteger calculatedSecret = DiffieHellman.secret(primeP, publicKey, privateKey);

        assertEquals(expectedSecret, calculatedSecret);
    }

    @Test
    public void private_key_in_range() {
        BigInteger primeP = BigInteger.valueOf(23);
        for (int i = 0; i < 100; i++) {
            BigInteger privateKey = DiffieHellman.randomPrivateKey(primeP);
            assertTrue(privateKey.compareTo(BigInteger.valueOf(2)) >= 0);
            assertTrue(privateKey.compareTo(primeP.subtract(BigInteger.valueOf(1))) <= 0);
        }
    }

    @Test
    public void private_key_randomly_generated() {
        BigInteger primeP = BigInteger.valueOf(7919);
        int count = 100;
        List<BigInteger> privateKeys = new ArrayList<BigInteger>();
        for (int i = 0; i < count; i++) {
            privateKeys.add(i, DiffieHellman.randomPrivateKey(primeP));
        }

        // be a little lenient because out of 100, we are bound to get duplicates when range is just 7919
        int leniency = 5;
        assertTrue(count - new HashSet<BigInteger>(privateKeys).size() < leniency);
    }

    @Test
    public void public_key_correctly_calculated() {
        BigInteger primeP = BigInteger.valueOf(23);
        BigInteger primeG = BigInteger.valueOf(5);
        BigInteger privateKey = BigInteger.valueOf(6);

        BigInteger actual = DiffieHellman.publicKey(primeP, primeG, privateKey);
        assertEquals(BigInteger.valueOf(8), actual);
    }

    @Test
    public void secret_key_correctly_calculated() {
        BigInteger primeP = BigInteger.valueOf(23);
        BigInteger publicKey = BigInteger.valueOf(19);
        BigInteger privateKey = BigInteger.valueOf(6);

        BigInteger actual = DiffieHellman.secret(primeP, publicKey, privateKey);
        assertEquals(BigInteger.valueOf(2), actual);
    }

    @Test
    public void random_techniques_test() {
        BigInteger maxInt = new BigInteger("32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");

        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            BigInteger ri = RandomHelper.randomBigIntMethod1(maxInt);
//            System.out.println(ri);
        }
        System.out.println("1000 randoms using method 1: " + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            BigInteger ri = RandomHelper.randomBigIntMethod2(maxInt);
//            System.out.println(ri);
        }
        System.out.println("1000 randoms using method 2: " + (System.currentTimeMillis() - time));
    }

}
