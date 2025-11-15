package com.waffleandhshake.Service;


import com.waffleandhshake.DTO.SectionItemRequest;
import com.waffleandhshake.Entity.Section;
import com.waffleandhshake.Entity.SectionItem;
import com.waffleandhshake.Repository.SectionItemRepository;
import com.waffleandhshake.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SectionItemService {

    @Autowired
    private SectionItemRepository itemRepository;

    @Autowired
    private SectionRepository sectionRepository;

    public SectionItem save(SectionItemRequest request) {
        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        SectionItem item = new SectionItem();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImagePath(request.getImagePath());
        item.setSection(section);

        return itemRepository.save(item);
    }

    public SectionItem update(Long id, SectionItemRequest request) {
        SectionItem item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImagePath(request.getImagePath());

        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public List<SectionItem> getBySection(Long sectionId) {
        return itemRepository.findBySectionId(sectionId);
    }

    public List<SectionItem> getAll() {
        return itemRepository.findAll();
    }

}
