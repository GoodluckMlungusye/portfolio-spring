package com.goodamcodes.service;

import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.mapper.NavigationLinkMapper;
import com.goodamcodes.model.NavigationLink;
import com.goodamcodes.repository.NavigationLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NavigationLinkService {

    private final NavigationLinkRepository navigationLinkRepository;
    private final NavigationLinkMapper navigationLinkMapper;

    public NavigationLinkDTO addNavigationLink(NavigationLinkDTO navigationLinkDTO){
        Optional<NavigationLink> existingNavigationLink = navigationLinkRepository.findByName(navigationLinkDTO.getName());
        if(existingNavigationLink.isPresent()){
            throw new IllegalStateException("Navigation Link exists");
        }
        NavigationLink navigationLink = navigationLinkMapper.toNavigationLink(navigationLinkDTO);
        NavigationLink savedNavigationLink = navigationLinkRepository.save(navigationLink);
        return navigationLinkMapper.toNavigationLinkDTO(savedNavigationLink);
    }

    public List<NavigationLinkDTO> addAllNavigationLinks(List<NavigationLinkDTO> navigationLinkDTOs) {
        List<NavigationLink> navigationLinks = navigationLinkMapper.toNavigationLinks(navigationLinkDTOs);

        List<NavigationLink> newNavigationLinks = navigationLinks.stream()
                .filter(link -> navigationLinkRepository.findByName(link.getName()).isEmpty())
                .toList();

        List<NavigationLink> savedLinks = navigationLinkRepository.saveAll(newNavigationLinks);
        return navigationLinkMapper.toNavigationLinkDTOs(savedLinks);
    }

    public List<NavigationLinkDTO> getNavigationLinks(){
        List<NavigationLink> navigationLinks = navigationLinkRepository.findAll();
        return navigationLinkMapper.toNavigationLinkDTOs(navigationLinks);
    }

    public NavigationLinkDTO updateNavigationLink(Long navigationLinkId, NavigationLinkDTO navigationLinkDTO){
        NavigationLink existingNavigationLink = navigationLinkRepository.findById(navigationLinkId).orElseThrow(
                () -> new IllegalStateException("Navigation Link does not exist")
        );
        navigationLinkMapper.updateNavigationLinkFromDTO(navigationLinkDTO, existingNavigationLink);
        NavigationLink updatedNavigationLink = navigationLinkRepository.save(existingNavigationLink);
        return navigationLinkMapper.toNavigationLinkDTO(updatedNavigationLink);
    }

    public String deleteNavigationLink(Long navigationLinkId){
        boolean isExisting = navigationLinkRepository.existsById(navigationLinkId);
        if(!isExisting){
            throw new IllegalStateException("Navigation Link does not exist");
        }
        navigationLinkRepository.deleteById(navigationLinkId);
        return "Navigation link with id: " + navigationLinkId + " has been deleted";
    }

}
