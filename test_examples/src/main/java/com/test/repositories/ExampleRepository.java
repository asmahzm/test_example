//package com.test.repositories;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.transaction.Transactional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.test.entities.ExampleTable;
//
//@Transactional
//@Repository
//public interface ExampleRepository extends JpaRepository<ExampleTable, String> {
//
//	@Query(value = "SELECT * FROM example")
//	List<Map<String, Object>> getDataList();
//	
//	@Query(value = "SELECT example_name, example_job FROM example WHERE example_id = ?1")
//	Map<String, Object> getDataById(int id);
//	
//	@Modifying
//	@Query(value = "INSERT INTO example (example_id, example_name, example_age, example_job) "
//		+ "VALUES(?1, ?2, ?3, ?4)")
//	int insertData(int id, String name, int age, String job);
//	
//	@Modifying
//	@Query(value = "UPDATE example SET example_job = ?1 WHERE example_name = ?2")
//	int updateDataJob(String job, String name);
//	
//}
