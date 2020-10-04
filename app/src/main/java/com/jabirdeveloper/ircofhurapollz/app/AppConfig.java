package com.jabirdeveloper.ircofhurapollz.app;

import com.jabirdeveloper.ircofhurapollz.model.KategoriModel;

import java.util.ArrayList;

public class AppConfig {
    public static final String WEBSITE_URL = "https://hurapollz.net/";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String LOAD_PER_PAGE = "20";
    public static final String FCM_API_KEY = "hoq^C93nWjj2kXGO4owcNgcL";
    public static final ArrayList<KategoriModel> KATEGORI_LIST = new ArrayList<KategoriModel>(){{
        add(new KategoriModel("","Terkini"));
        add(new KategoriModel("90", "Cerpen"));
        add(new KategoriModel("92", "History"));
        add(new KategoriModel("96", "Puisi"));
        add(new KategoriModel("91", "Creepypasta"));
        add(new KategoriModel("93", "Knowledge"));
        add(new KategoriModel("97", "Riddle"));
        add(new KategoriModel("1", "Other"));
    }};
    public static final int HEADER_POST = 1;
    public static final int ITEM_PER_HEADER = 3;

    public static final String[] KATA_KATA = {
            "“Seribu orang tua bisa bermimpi, satu orang pemuda bisa mengubah dunia.”\n\n- Soekarno",
            "“Anda tidak bisa pergi dari tanggungjawab esok hari dengan menghindarinya hari ini”\n\n- Abraham Lincoln",
            "“Pendidikan adalah senjata paling ampuh untuk mengubah dunia.”\n\n- Nelson Mandela",
            "“Dunia ini cukup untuk memenuhi kebutuhan manusia, bukan untuk memenuhi keserakahan manusia.”\n\n- Mahatma Gandhi",
            "“Anda ingin mengetahui siapa diri Anda? Jangan bertanya. Beraksilah!”\n\n- Thomas Jefferson",
            "“Arahkan mata Anda pada bintang-bintang dengan kaki tetap berpijak pada tanah.”\n\n- Theodore Roosevelt",
            "“Usaha dan keberanian tidak cukup tanpa tujuan dan arah perencanaan”\n\n- John F. Kennedy",
            "“Semuanya kelihatan tidak mungkin sampai segala sesuatu selesai.”\n\n- Nelson Mandela",
            "“Jika kamu berpikir kamu terlalu kecil untuk membuat sebuah perubahan, cobalah tidur di ruangan dengan seekor nyamuk.”\n\n- Dalai Lama",
            "“Anda mungkin bisa menunda, tapi waktu tidak akan menunggu”\n\n- Benjamin Franklin",
            "“Kesenangan dalam sebuah pekerjaan membuat kesempurnaan pada hasil yang dicapai.”\n\n- Aristoteles",
            "“Sebelum apapun, persiapan adalah kunci menuju kesuksesan.”\n\n- Alexander Graham Bell",
            "“Satu-satunya sumber dari pengertahuan adalah pengalaman.”\n\n- Albert Einstein",
            "“Penemuan terbesar sepanjang masa adalah bahwa seseorang bisa mengubah masa depannya hanya dengan mengubah sikapnya saat ini.”\n\n- Oprah Winfrey",
            "“Yang membuatku terus berkembang adalah tujuan-tujuan hidupku.”\n\n- Muhammad Ali",
            "“Tidak masalah seberapa lambat kau berjalan asalkan kau tidak berhenti.”\n\n- Confucius",
            "“Diam adalah sumber dari kekuatan yang luar biasa.”\n\n- Lao Tzu",
            "“Beri nilai dari usahanya jangan dari hasilnya. Baru kita bisa menilai kehidupan.”\n\n- Albert Einstein",
            "“Hitunglah umurmu dengan teman, bukan tahun. Hitunglah hidupmu dengan senyum, bukan air mata.”\n\n- John Lennon",
            "“Kesuksesan dan kegagalan adalah sama-sama bagian dalam hidup. Keduanya hanyalah sementara.”\n\n- Shah Rukh Khan",
            "“Pekerjaan-pekerjaan kecil yg selesai dilakukan lebih baik daripada rencana-rencana besar yg hanya didiskusikan.”\n\n- Peter Marshall",
            "“Beberapa orang memimpikan kesuksesan, sementara yang lain bangun setiap pagi untuk mewujudkannya.”\n\n- Wayne Huizenga",
            "“Kebahagiaan sama dengan kenyataan dikurangi ekspektasi.”\n\n- Tom Magliozzi",
            "“Kamu tidak perlu menjadi luar biasa untuk memulai, tapi kamu harus memulai untuk menjadi luar biasa.”\n\n- Zig Ziglar",
            "“Untuk sukses, sikap sama pentingnya dengan kemampuan.”\n\n- Walter Scott",
            "“Kamu tidak bisa kembali dan mengubah awal saat kamu memulainya, tapi kamu bisa memulainya lagi dari di mana kamu berada sekarang dan ubah akhirnya.”\n\n- C.S Lewis",
            "“Tidak ada yang akan berhasil kecuali kau melakukannya.”\n\n- Maya Angelou",
            "“Hadapkan wajahmu selalu ke arah matahari, sehingga bayangan akan jatuh di belakangmu.”\n\n- Walt Whitman",
            "“Jangan habiskan waktumu memukuli dinding dan berharap bisa mengubahnya menjadi pintu.”\n\n- Coco Canel",
            "“Aku memilih untuk membuat sisa hidupku menjadi yang terbaik dalam hidupku.”\n\n- Louise Hay",
            "“Kita harus berarti untuk diri kita sendiri dulu sebelum kita menjadi orang yang berharga bagi orang lain.”\n\n- Ralph Waldo Emerson",
            "“Cinta adalah ketika kebahagiaan orang lain lebih penting dari kebahagiaanmu.”\n\n- H. Jackson Brown",
            "“Cinta itu seperti angin. Kau tak dapat melihatnya, tapi kau dapat merasakannya.”\n\n- Nicholas Sparks",
            "“Bagaimana kau mengeja ‘cinta’? tanya Piglet. “Kau tak usah mengejanya….rasakan saja.” Jawab Pooh. A.A Milne",
            "“Hubungan asmara itu seperti kaca. Terkadang lebih baik meninggalkannya dalam keadaan pecah. Daripada menyakiti dirimu dengan cara menyatukan mereka kembali”\n\n- D.Love",
            "“Hal yang paling menyakitkan adalah kehilangan jati dirimu saat engkau terlalu mencintai seseorang. Serta lupa bahwa sebenarnya engkau juga spesial”\n\n- Ernest Hemingway",
            "“Cinta terjadi begitu singkat, namun melupakan memakan waktu begitu lama”\n\n- Pablo Neruda",
            "“Seseorang tak akan pernah tahu betapa dalam kadar cintanya sampai terjadi sebuah perpisahan”\n\n- Kahlil Gibran",
            "“Asmara bukan hanya sekedar saling memandang satu sama lain. Tapi juga sama sama melihat ke satu arah yang sama.”\n\n- Antoine de Saint-Exupéry",
            "“Beberapa orang akan pergi dari hidupmu, tapi itu bukan akhir dari ceritamu. Itu cuma akhir dari bagian mereka di ceritamu”\n\n- Faraaz Kazi"
    };

}
