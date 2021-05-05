package com.trainingapp.trainingapp.service

import com.trainingapp.trainingapp.domain.Exercise
import com.trainingapp.trainingapp.domain.Training
import com.trainingapp.trainingapp.model.ExerciseDTO
import com.trainingapp.trainingapp.model.NewTrainingDTO
import com.trainingapp.trainingapp.model.TrainingDTO
import com.trainingapp.trainingapp.model.UserDTO
import com.trainingapp.trainingapp.repository.ExerciseRepository
import com.trainingapp.trainingapp.repository.TrainingRepository
import com.trainingapp.trainingapp.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Slf4j
@Service
class TrainingService {

    @Autowired
    TrainingRepository trainingRepository
    @Autowired
    UserRepository userRepository
    @Autowired
    ExerciseRepository exerciseRepository
    @Autowired
    ExerciseService exerciseService

    List<TrainingDTO> trainingByUser(String name) {
        trainingRepository
                .findByUserName(name)
                .collect { new TrainingDTO(name: it.name, trainingDuration: it.trainingDuration) }
    }

    List<TrainingDTO> trainingByEmail(String email) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        trainingRepository
                .findByUserEmail(email)
                .collect { new TrainingDTO(id: it.id, name: it.name, trainingDuration: it.trainingDuration, data: formatter.format(it.data)) }
                .reverse()


    }

    Optional<NewTrainingDTO> addTraining(NewTrainingDTO newTraining) {
            def sum = 0
            if(newTraining.name) {
                newTraining
                        .listExerciseDto
                        .each { it -> sum += it.time }
                def val = trainingRepository.save(new Training(
                        name: newTraining.name,
                        data: LocalDateTime.now(),
                        trainingDuration: sum,
                        user: userRepository.findUserByEmail(newTraining.email)))
                val.exercise = newTraining
                        .listExerciseDto
                        .collect { it ->
                            exerciseRepository.save(new Exercise(name: it.name,
                                    description: it.description,
                                    time: it.time,
                                    training: val))
                        }

                Optional.of(newTraining)
            }
        }

    List<ExerciseDTO> trainingByName(String name) {
        exerciseRepository
                .findByTrainingName(name)
                .collect {
                    new ExerciseDTO(name: it.name,
                            description: it.description,
                            time: it.time)
                }
    }

    Optional<NewTrainingDTO> updOrCreateTraining(NewTrainingDTO newTrainingDTO) {
        def val = trainingRepository.findByName(newTrainingDTO.name)
        if (val.isPresent()) {
            deleteOneTraining(val.get().name)
            //  log.info "Training $newTrainingDTO.name was deleted"
            addTraining(newTrainingDTO)
            log.info "Training $newTrainingDTO.name was updated"
        } else addTraining(newTrainingDTO)// удаляем упражнения и записываем новые

        Optional.of(newTrainingDTO)
    }

//    Optional<String> nameTraining(String name) {
//        trainingRepository
//                .findByName(name)
//                .map { Optional.of(it.name) }
//                .orElse(Optional.empty())
//
//    }

    void deleteTraining(String email) {
        def trainingForDel = trainingRepository
                .findByUserEmail(email)
        if (trainingForDel) {
            trainingForDel.each { it -> exerciseService.deleteExercise(it.name) }
            trainingRepository.deleteAll(trainingForDel)
        }
    }

    void deleteOneTraining(String name) {
        def trainingForDel = trainingRepository
                .findByName(name)
        if (trainingForDel) {
            exerciseService.deleteExercise(name)
            trainingRepository.delete(trainingForDel.get())
        }
    }

    void deleteOneTrainingById(Long id) {
        def trainingForDel = trainingRepository
                .findById(id)
        if (trainingForDel) {
            exerciseService.deleteExercise(trainingForDel.get().name)
            trainingRepository.delete(trainingForDel.get())
        }
    }
}