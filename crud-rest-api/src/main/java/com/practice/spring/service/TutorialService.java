package com.practice.spring.service;

import com.practice.spring.entity.Tutorial;
import com.practice.spring.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;


    public Page<Tutorial> getAllTutorials(){
        return tutorialRepository.findAll(Pageable.unpaged());
    }

    public Page<Tutorial> getTutorialByTitle(String title, Pageable pageable){
        return tutorialRepository.findByTitleContaining(title, pageable);
    }

    public Page<Tutorial> getByPublished(boolean published, Pageable pageable){
        return tutorialRepository.findByPublished(published, pageable);
    }

    public Tutorial getTutorialById(long tutorialId) throws Exception {
        return tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new Exception(String.format("tutorial Id: %s is not found", tutorialId)));
    }

    public Tutorial createTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public Tutorial editTutorial(long id, Tutorial tutorial) throws Exception {
        Tutorial tutorialById = getTutorialById(id);
        return tutorialRepository.save(tutorialById);
    }

    public void deleteTutorial(long id) throws Exception {
        Tutorial tutorialById = getTutorialById(id);
        if(tutorialById != null)
            tutorialRepository.deleteById(id);
    }

    public void deleteAllTutorial() {
        tutorialRepository.deleteAll();
    }
}
