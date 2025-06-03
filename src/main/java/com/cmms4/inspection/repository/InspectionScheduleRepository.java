package com.cmms4.inspection.repository; // << UPDATED PACKAGE

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmms4.inspection.entity.InspectionSchedule;
import com.cmms4.inspection.entity.InspectionScheduleIdClass;

import java.util.List;
import java.util.Optional;

// Imports for InspectionSchedule and InspectionScheduleIdClass are not strictly needed here
// as they are in the same package.

@Repository
public interface InspectionScheduleRepository extends JpaRepository<InspectionSchedule, InspectionScheduleIdClass> {

    /**
     * 회사 ID와 점검 ID로 점검 일정 목록을 조회합니다.
     * 일정 날짜 기준 오름차순 정렬됩니다.
     *
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @return 점검 일정 엔티티 목록
     */
    List<InspectionSchedule> findByCompanyIdAndInspectionIdOrderByScheduleDateAsc(String companyId, Integer inspectionId);

    /**
     * 회사 ID, 점검 ID, 일정 ID로 특정 점검 일정을 조회합니다.
     *
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @param scheduleId 일정 ID
     * @return 점검 일정 엔티티 (Optional)
     */
    Optional<InspectionSchedule> findByCompanyIdAndInspectionIdAndScheduleId(String companyId, Integer inspectionId, Integer scheduleId);
}
