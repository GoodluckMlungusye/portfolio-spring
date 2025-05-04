package com.goodamcodes.service;

import com.goodamcodes.dto.ExploreDTO;
import com.goodamcodes.mapper.ExploreMapper;
import com.goodamcodes.model.Explore;
import com.goodamcodes.model.ServiceOffered;
import com.goodamcodes.repository.ExploreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExploreService {

    @Autowired
    private ExploreRepository exploreRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    ExploreMapper exploreMapper;

    public ExploreDTO addExplore(ExploreDTO exploreDTO, MultipartFile file) {
        Optional<Explore> existingExplore = exploreRepository.findByDescription(exploreDTO.getDescription());
        if (existingExplore.isPresent()) {
            throw new IllegalStateException("Explore already exists");
        }
        Explore explore = exploreMapper.toExplore(exploreDTO);

        if(file != null && !file.isEmpty()) {
            String fileName = imageService.saveImage(file);
            explore.setImage(fileName);
        }
        Explore savedExplore = exploreRepository.save(explore);
        return exploreMapper.toExploreDTO(savedExplore);
    }

    public List<ExploreDTO> addAllExplores(List<ExploreDTO> exploreDTOs, List<MultipartFile> files) {
        List<Explore> explores = exploreMapper.toExplores(exploreDTOs);

        List<Explore> newExplores = IntStream.range(0, explores.size())
                .filter(i -> exploreRepository.findByDescription(exploreDTOs.get(i).getDescription()).isEmpty())
                .mapToObj(i -> {
                    Explore explore = explores.get(i);
                    String fileName = imageService.handleFilesUpload(files, i);
                    if (fileName != null) {
                        explore.setImage(fileName);
                    }
                    return explore;
                })
                .collect(Collectors.toList());

        List<Explore> savedExplores = exploreRepository.saveAll(newExplores);
        return exploreMapper.toExploreDTOs(savedExplores);
    }


    public List<ExploreDTO> getExploreList(){
        List<Explore> exploreList = exploreRepository.findAll();
        return exploreMapper.toExploreDTOs(exploreList);
    }

    public ExploreDTO updateExplore(Long exploreId, ExploreDTO exploreDTO, MultipartFile file){
        Explore existingExplore = exploreRepository.findById(exploreId).orElseThrow(
                () -> new IllegalStateException("Explore Item does not exist")
        );

        exploreMapper.updateExploreFromDTO(exploreDTO, existingExplore);

        if(file != null && !file.isEmpty()) {
            imageService.deleteImage(existingExplore.getImage());
            String fileName = imageService.saveImage(file);
            existingExplore.setImage(fileName);
        }

        Explore updatedExplore = exploreRepository.save(existingExplore);
        return exploreMapper.toExploreDTO(updatedExplore);
    }

    public String deleteExplore(Long exploreId){
        Explore explore = exploreRepository.findById(exploreId).orElseThrow(
                () -> new IllegalStateException("Explore Item does not exist")
        );
        imageService.deleteImage(explore.getImage());
        exploreRepository.deleteById(exploreId);
        return "Explore with id: " + exploreId + " has been deleted";
    }

}
