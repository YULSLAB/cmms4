package com.cmms4.inspection.service;

import com.cmms4.inspection.entity.Inspection;
import com.cmms4.inspection.entity.InspectionSchedule;
import com.cmms4.inspection.entity.InspectionItem;

import com.cmms4.inspection.repository.InspectionRepository;
import com.cmms4.inspection.repository.InspectionScheduleRepository;
import com.cmms4.inspection.repository.InspectionItemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * cmms4 - InspectionService
 * 점검 관리 서비스
 * 
 * @author cmms4
 * @since 2025-05-29
 */
@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final InspectionScheduleRepository inspectionScheduleRepository;
    private final InspectionItemRepository inspectionItemRepository;

    public InspectionService(
            InspectionRepository inspectionRepository,
            InspectionScheduleRepository inspectionScheduleRepository,
            InspectionItemRepository inspectionItemRepository) {
        this.inspectionRepository = inspectionRepository;
        this.inspectionScheduleRepository = inspectionScheduleRepository;
        this.inspectionItemRepository = inspectionItemRepository;
    }

    /**
     * 모든 점검 목록을 페이지네이션으로 조회
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @param pageable 페이지 정보
     * @return 점검 목록 페이지
     */
    @Transactional(readOnly = true)
    public Page<Inspection> getAllInspections(String companyId, String siteId, Pageable pageable) {
        return inspectionRepository.findByCompanyIdAndSiteIdAndDeleteMarkIsNull(companyId, siteId, pageable);
    }
    
    /**
     * 점검 ID로 점검 정보 조회
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @return 점검 정보 Optional
     */
    @Transactional(readOnly = true)
    public Optional<Inspection> getInspectionByInspectionId(String companyId, String inspectionId) {
        return inspectionRepository.findByCompanyIdAndInspectionIdAndDeleteMarkIsNull(companyId, inspectionId);
    }

    /**
     * plantId로 점검 정보 조회
     * @param companyId 회사 ID
     * @param plantId 설비 ID
     * @return 점검 정보 Optional
     */
    @Transactional(readOnly = true)
    public Page<Inspection> getInspectionByPlantId(String companyId, String plantId, Pageable pageable) {
        return inspectionRepository.findByCompanyIdAndPlantIdAndDeleteMarkIsNull(companyId, plantId, pageable);
    }

    /**
     * 점검 정보 저장 (신규 등록 또는 수정)
     * @param inspection 저장할 점검 정보
     * @return 저장된 점검 정보
     */
    @Transactional
    public Inspection saveInspection(Inspection inspection, String username) {
        LocalDateTime now = LocalDateTime.now();
        Integer maxInspectionId = inspectionRepository.findMaxInspectionIdByCompanyId(inspection.getCompanyId());
        int newInspectionId = (maxInspectionId == null) ? 300000000 : maxInspectionId + 1;
        inspection.setInspectionId(newInspectionId);
        inspection.setCreateDate(now);
        inspection.setCreateBy(username);

        return inspectionRepository.save(inspection);
    }

    /**
     * 점검 정보 삭제 (소프트 삭제)
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     */
    @Transactional
    public void deleteInspection(String companyId, String inspectionId) {
        Optional<Inspection> inspectionOpt = inspectionRepository.findByCompanyIdAndInspectionIdAndDeleteMarkIsNull(companyId, inspectionId);
        if (inspectionOpt.isPresent()) {
            Inspection inspection = inspectionOpt.get();
            inspectionRepository.delete(inspection);
        } else {
            throw new RuntimeException("Inspection not found with ID: " + inspectionId);
        }
    }
    /** InspectionSchedule에 대한 부분  */
    
    /**
     * inspection ID로 점검 schedule 조회
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @return 점검 목록 List
     */
    @Transactional(readOnly = true)
    public List<InspectionSchedule> getInspectionsByInspectionId(String companyId, String inspectionId) {
        return inspectionScheduleRepository.findByCompanyIdAndInspectionIdOrderByScheduleDateAsc(companyId, inspectionId);
    }

    /**
     * Schedule ID로 점검 정보 조회
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @param scheduleId 일정 ID
     * @return 점검 정보 Optional
     */
    @Transactional(readOnly = true)
    public Optional<InspectionSchedule> getInspectionByScheduleId(String companyId, String inspectionId, String scheduleId) {
        return inspectionScheduleRepository.findByCompanyIdAndInspectionIdAndScheduleId(companyId, inspectionId, scheduleId);
    }

    /** InspectionItem에 대한 부분  */

    /**
     * 점검 항목 목록 조회
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @param scheduleId 일정 ID
     * @return 점검 항목 목록 List
     */
    @Transactional(readOnly = true)
    public List<InspectionItem> getInspectionItems(String companyId, String inspectionId, String scheduleId) {
        return inspectionItemRepository.findByCompanyIdAndInspectionIdAndScheduleIdOrderByItemIdAsc(companyId, inspectionId, scheduleId);
    }
    /**
     * 점검 항목 조회
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @param scheduleId 일정 ID
     * @param itemId 항목 ID
     * @return 점검 항목 Optional
     */
    @Transactional(readOnly = true)
    public Optional<InspectionItem> getInspectionItem(String companyId, String inspectionId, String scheduleId, String itemId) {
        return inspectionItemRepository.findByCompanyIdAndInspectionIdAndScheduleIdAndItemId(companyId, inspectionId, scheduleId, itemId);
    }
}