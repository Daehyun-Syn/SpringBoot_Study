package kopo.poly.controller;

import kopo.poly.dto.MelonDTO;
import kopo.poly.service.IMelonService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class MelonController {

    @Resource(name = "MelonService")
    private IMelonService melonService;

    /**
     * 멜론 노래 리스트 저장하기
     */
    @GetMapping(value = "melon/collectMelonSong")
    public String collectMelonRank() throws Exception{

        log.info("MelonController : collectMelonRank 시작 !");

        // 수집 결과 출력
        String msg;

        int res = melonService.collectMelonSong();

        if(res == 1) {
            msg = "success";

        }else{
            msg = "fail";
        }

        log.info("MelonController : collectMelonRank 끝 !");

        return msg;

    }

    /**
     * 오늘 수집된 멜론 노래리스트 가져오기
     */
    @GetMapping(value = "melon/getSongList")
    public List<MelonDTO> getSongList() throws Exception {

        log.info("MelonController : getSongList시작 !");

        List<MelonDTO> rList = melonService.getSongList();

        log.info(this.getClass().getName() + "getSongList End!");

        return rList;
    }

    /**
     * 가수별 수집된 노래의 수 가져오기
     */
    @GetMapping(value = "melon/getSingerSongCnt")
    public List<MelonDTO> getSingerSongCnt() throws Exception {

        log.info("MelonController : getSongList시작 !");

        List<MelonDTO> rList = melonService.getSingerSongCnt();

        log.info("MelonController : getSongList 끝 !");

        return rList;
    }

    /**
     * 가수별 수집된 노래 가져오기
     */
    @GetMapping(value = "melon/getSingerSong")
    public List<MelonDTO> getSingerSong() throws Exception{

        log.info("MelonController : getSingerSong 시작 !");

        List<MelonDTO> rList = melonService.getSingerSong();
        log.info("MelonController : getSingerSong 끝 !");

        return rList;
    }

    /**
     * 멜론 노래 리스트 저장하기
     */
    @GetMapping(value = "melon/collectMelonSongMany")
    public String collectMelonSongMany() throws Exception{

        log.info("MelonController : collectMelonSongMany 시작 !");

        // 수집 결과 출력
        String msg;

        int res = melonService.collectMelonSongMany();

        if(res ==1) {
            msg = "success";
        } else{
            msg = "fail";
        }

        log.info("MelonController : collectMelonSongMany 끝 !");

        return msg;
    }

    /**
     * 가수 이름이 방탄소년단을 BTS로 변경하기
     */
    @GetMapping(value = "melon/updateBTSName")
    public String updateBTSName() throws Exception {

        log.info("MongoController : updateBTSName 시작");

        //결과 출력
        String msg;

        int res = melonService.updateBTSName();

        if(res == 1) {
            msg = "success";

        }else {
            msg = "fail";
        }

        log.info("MongoController : updateBTSName 끝");

        return msg;

    }
    /**
     * 가수 이름이 방탄소년단을 BTS로 변경하기
     */
    @GetMapping(value = "melon/btsAddNickname")
    public String btsAddField() throws Exception {

        log.info("MongoController : btsAddField 시작");

        // 결과 출력
        String msg;

        int res = melonService.updateAddBTSNickname();

        if(res == 1) {
            msg = "success";

        } else{
            msg = "fail";
        }

        log.info("MongoController : btsAddField 끝");

        return msg;
    }
    /**
     * 방탄소년단의 멤버 추가하기
     */
    @GetMapping(value = "melon/btsAddMember")
    public String btsAddMember() throws Exception {

        log.info("MongoController : btsAddMember 시작");

        // 결과 출력
        String msg;

        int res = melonService.updateAddBTSMember();

        if(res == 1) {
            msg = "success";

        } else{
            msg = "fail";
        }

        log.info("MongoController : btsAddMember 끝");

        return msg;
    }


    /**
     * 멜론 노래 리스트 저장하기
     */
    @GetMapping(value = "melon/deleteSong")
    public String deleteSong() throws Exception {

        log.info("MongoController : deleteSong 시작");

        // 수집 결과 출력
        String msg;

        int res = melonService.deleteSong();

        if(res == 1) {
            msg = "success";

        } else{
            msg = "fail";
        }

        log.info("MongoController : deleteSong 끝");

        return msg;
    }

    /**
     *
     * 가수 이름이 방탄소년단을 BTS로 변경하기
     */
    @GetMapping(value = "melon/updateManySong")
    public String updateManySong() throws Exception{

        log.info(this.getClass().getName() + ".updateManySong Start");

        String msg;

        int res = melonService.updateManySong();

        if (res == 1){

            msg = "success";
        }else {
            msg =  "fail";
        }
        log.info(this.getClass().getName() + ".updateManySong End");

        return msg;
    }
}
