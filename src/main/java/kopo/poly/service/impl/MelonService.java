package kopo.poly.service.impl;

import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.service.IMelonService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("MelonService")
public class MelonService implements IMelonService {

    @Resource(name = "MelonMapper")
    private IMelonMapper melonMapper; // MongoDB에 저장할 Mapper

    @Override
    public int collectMelonSong() throws Exception {

        // 로그 찍기
        log.info("MelonService : collectMelonSong 시작 !");

        int res = 0;

        List<MelonDTO> pList = new LinkedList<>();

        // 멜론 Top100 중 50위까지 정보 가져오는 페이지
        String url = "https://www.melon.com/chart/index.htm";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML소스 저장할 변수
        Document doc = Jsoup.connect(url).get();

        // <div class="service_list_song"> 이 태그 내에서 있는 HTML소스만 element에 저장됨
        Elements element = doc.select("div.service_list_song");

        // 멜론 100위까지 차트
        for (Element songInfo : element.select("div.wrap_song_info")){

            //크롤링을 통해 데이터 저장하기
            String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text()); // 노래
            String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").eq(0).text()); // 가수

            log.info("MelonService.collectMelonSong Song : " + song);
            log.info("MelonService.collectMelonSong Singer : " + singer);

            // 가수와 노래 정보가 모두 수집되었다면, 저장함
            if ((song.length() > 0) && (singer.length() > 0)){

                MelonDTO pDTO = new MelonDTO();
                pDTO.setCollectTime(DateUtil.getDateTime("yyyyMMddhhmmss"));
                pDTO.setSong(song);
                pDTO.setSinger(singer);

                // 한번에 여러개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기
                pList.add(pDTO);

            }
        }
        // 생성할 컬렉션명
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // MongoDB에 데이터 저장하기
        res = melonMapper.insertSong(pList, colNm);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info("MelonService : collectMelonSong 끝 !");

        return res;


    }

    @Override
    public List<MelonDTO> getSongList() throws Exception {

        log.info("MelonService : getSongList 시작 !");

        // MongoDB에 저장된 컬렉션 이름
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = new LinkedList<>();

        rList = melonMapper.getSongList(colNm);

        if(rList == null) {
            rList = new LinkedList<>();
        }

        log.info("MelonService : getSongList 끝 !");

        return rList;
    }
    
    @Override
    public List<MelonDTO> getSingerSongCnt() throws Exception{
        
        log.info("MelonService : getSingerSongCnt 시작 !");
        
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");
        
        List<MelonDTO> rList = melonMapper.getSingerSongCnt(colNm);
        
        if(rList == null) {
            rList = new LinkedList<>();
        }
        
        log.info("MelonService : getSingerSongCnt 끝 !");

        return rList;
    }

    @Override
    public List<MelonDTO> getSingerSong() throws Exception{

        log.info("MelonService : getSingerSong 시작 !");

        // MongoDB에 저장된 컬렉션 이름
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 수집된 데이터로부터 검색할 가수명
        String singer = "방탄소년단";

        // 결과값
        List<MelonDTO> rList = null;

        // Melon 노래 수집하기
        if(this.collectMelonSong()==1){

            rList = melonMapper.getSingerSong(colNm, singer);

            if(rList == null){
                rList = new LinkedList<>();

            }

        }else {
            rList = new LinkedList<>();

        }
        log.info("MelonService : getSingerSong 끝 !");
        return rList;
    }

    @Override
    public int collectMelonSongMany() throws Exception {

        log.info("MelonService : collectMelonSongMany 시작 !");

        int res = 0;

        List<MelonDTO> pList = new LinkedList<>();

        //멜론 top100 중 100위까지 정보 가져오는 페이지
        String url = "https://www.melon.com/chart/index.htm";

        //jsoup 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML소스 저장할 변수
        Document doc = Jsoup.connect(url).get();

        //<div class="service_list_song"> 이 태그 내에서 있는 HTML소스만 element에 저장됨
        Elements elements = doc.select("div.service_list_song");

        //Iterator을 사용하여 멜론차트 정보를 가져오기
        // 멜론 100위 까지 차트
        for(Element songInfo : elements.select("div.wrap_song_info")){

            // 크롤링을 통해 데이터 저장하기
            String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text());
            String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").text());

            log.info("song : " + song);
            log.info("singer : " + singer);

            // 가수와 노래 정보가 모두 수집되었다면, 저장함
            if((song.length() > 0) && (singer.length() > 0)) {

                MelonDTO pDTO = new MelonDTO();
                pDTO.setCollectTime(DateUtil.getDateTime("yyyyMMddhhmmss"));
                pDTO.setSong(song);
                pDTO.setSinger(singer);

                // 한번에 여러개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기
                pList.add(pDTO);


            }
        }
        // 생성할 컬렉션명
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // MongoDB에 데이터 저장하기
        res = melonMapper.insertSongMany(pList, colNm);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info("MelonService : collectMelonSongMany 끝끝 !");
        return res;
    }
}
