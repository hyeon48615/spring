package net.fullstack10.service;

import lombok.extern.log4j.Log4j2;
import net.fullstack10.dao.BbsDAO;
import net.fullstack10.domain.BbsVO;
import net.fullstack10.dto.BbsDTO;
import net.fullstack10.util.MapperUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public enum BbsService {
    INSTANCE;

    private BbsDAO dao;
    private MapperUtil mapperUtil;

    BbsService() {
        dao = new BbsDAO();
        mapperUtil = MapperUtil.INSTANCE;
    }

    public int totalCount(Map<String, String> map) throws Exception {
        BbsDAO dao = new BbsDAO();
        return dao.getTotalCount(map);
    }

    public void regist(BbsDTO dto) throws Exception {
        // dto -> DAO(vo)

//        BbsVO vo = new BbsVO();
//        vo.setIdx(dto.getIdx());
//        vo.setTitle(dto.getTitle());
//        vo.setContent(dto.getContent());
//        vo.setUser_id(dto.getUser_id());
//        vo.setRead_cnt(dto.getRead_cnt());
//        vo.setCreated_at(dto.getCreated_at());
//        vo.setUpdated_at(dto.getUpdated_at());

        BbsVO vo = mapperUtil.get().map(dto, BbsVO.class);

        BbsDAO dao = new BbsDAO();
        dao.insert(vo);
    }

    public List<BbsDTO> list(Map<String, String> map) throws Exception {
//        List<BbsDTO> dtoList = IntStream.range(0, 10).mapToObj(i -> {
//            BbsDTO dto = new BbsDTO();
//            dto.setIdx(i);
//            dto.setUser_id("user" + i);
//            dto.setTitle("제목" + i);
//            dto.setContent("내용" + i);
//            dto.setCreated_at(LocalDateTime.now());
//            return dto;
//        }).collect(Collectors.toList());

        List<BbsVO> voList = dao.selectAll(map);
        List<BbsDTO> dtoList = voList.stream()
                .map(vo -> mapperUtil.get().map(vo, BbsDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }

    public BbsDTO view(int idx) throws Exception {
        BbsVO vo = dao.selectByidx(idx);
        BbsDTO dto = mapperUtil.get().map(vo, BbsDTO.class);

        return dto;
    }

    public void modify(BbsDTO dto) throws Exception {
        BbsVO vo = mapperUtil.get().map(dto, BbsVO.class);
        dao.update(vo);
    }

    public void delete(int idx) throws Exception {
        dao.delete(idx);
    }

    public void delete(List<Integer> idxList) throws Exception {
        dao.delete(idxList);
    }
}
