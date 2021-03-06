
package com.trainingapp.trainingapp.repository

import com.trainingapp.trainingapp.domain.Training
import org.springframework.data.jpa.repository.JpaRepository

interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByUserName(String s)

    List<Training> findByUserEmail(String s)

    Optional<Training> findByName(String s)

    Optional<Training> findById(Long s)
}