package com.waffleandhshake.Service;

import com.waffleandhshake.DTO.SectionRequest;
import com.waffleandhshake.Entity.Section;
import com.waffleandhshake.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section save(SectionRequest request) {
        Section section = new Section();
        section.setKey(request.getKey());
        section.setTitle(request.getTitle());
        section.setSubtitle(request.getSubtitle());
        section.setDescription(request.getDescription());
        return sectionRepository.save(section);
    }

    public Section update(Long id, SectionRequest request) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found"));
        section.setTitle(request.getTitle());
        section.setSubtitle(request.getSubtitle());
        section.setDescription(request.getDescription());
        return sectionRepository.save(section);
    }

    public void delete(Long id) {
        sectionRepository.deleteById(id);
    }

    public List<Section> getAll() {
        return sectionRepository.findAll();
    }

    public Section getByKey(String key) {
        return sectionRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Section not found with key " + key));
    }
}
