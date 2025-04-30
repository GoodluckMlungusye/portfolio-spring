package com.goodamcodes.service;

import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.mapper.NavigationLinkMapper;
import com.goodamcodes.model.NavigationLink;
import com.goodamcodes.repository.NavigationLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavigationLinkService {

    @Autowired
    private NavigationLinkRepository navigationLinkRepository;

    @Autowired
    NavigationLinkMapper navigationLinkMapper;

    public NavigationLinkDTO addNavigationLink(NavigationLinkDTO navigationLinkDTO){
        Optional<NavigationLink> existingNavigationLink = navigationLinkRepository.findByName(navigationLinkDTO.getName());
        if(existingNavigationLink.isPresent()){
            throw new IllegalStateException("Navigation Link exists");
        }
        NavigationLink navigationLink = navigationLinkMapper.toNavigationLink(navigationLinkDTO);
        NavigationLink savedNavigationLink = navigationLinkRepository.save(navigationLink);
        return navigationLinkMapper.toNavigationLinkDTO(savedNavigationLink);
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
