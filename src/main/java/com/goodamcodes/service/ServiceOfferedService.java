package com.goodamcodes.service;

import com.goodamcodes.dto.ServiceOfferedDTO;
import com.goodamcodes.mapper.ServiceOfferedMapper;
import com.goodamcodes.model.ServiceOffered;
import com.goodamcodes.repository.ServiceOfferedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ServiceOfferedService {

    private final ServiceOfferedRepository serviceOfferedRepository;
    private final ImageService imageService;
    private final ServiceOfferedMapper serviceOfferedMapper;

    public ServiceOfferedDTO addServiceOffered(ServiceOfferedDTO serviceOfferedDTO, MultipartFile file){
        Optional<ServiceOffered> existingServiceOffered = serviceOfferedRepository.findByName(serviceOfferedDTO.getName());
        if(existingServiceOffered.isPresent()){
            throw new IllegalStateException("Service exists");
        }

        ServiceOffered serviceOffered = serviceOfferedMapper.toServiceOffered(serviceOfferedDTO);

        if(file != null && !file.isEmpty()){
            String fileName = imageService.saveImage(file);
            serviceOffered.setImage(fileName);
        }

        ServiceOffered savedServiceOffered = serviceOfferedRepository.save(serviceOffered);
        return serviceOfferedMapper.toServiceOfferedDTO(savedServiceOffered);
    }

    public List<ServiceOfferedDTO> addAllServicesOffered(List<ServiceOfferedDTO> serviceDTOs, List<MultipartFile> files) {
        List<ServiceOffered> services = serviceOfferedMapper.toServicesOffered(serviceDTOs);

        List<ServiceOffered> newServices = IntStream.range(0, services.size())
                .filter(i -> serviceOfferedRepository.findByName(serviceDTOs.get(i).getName()).isEmpty())
                .mapToObj(i -> {
                    ServiceOffered service = services.get(i);
                    String fileName = imageService.handleFilesUpload(files, i);
                    if (fileName != null) {
                        service.setImage(fileName);
                    }
                    return service;
                })
                .collect(Collectors.toList());

        List<ServiceOffered> savedServices = serviceOfferedRepository.saveAll(newServices);
        return serviceOfferedMapper.toServiceOfferedDTOs(savedServices);
    }

    public List<ServiceOfferedDTO> getServiceOfferedList(){
        List<ServiceOffered> serviceOfferedList = serviceOfferedRepository.findAll();
        return serviceOfferedMapper.toServiceOfferedDTOs(serviceOfferedList);
    }

    public ServiceOfferedDTO updateServiceOffered(Long serviceOfferedId, ServiceOfferedDTO serviceOfferedDTO, MultipartFile file){
        ServiceOffered existingServiceOffered = serviceOfferedRepository.findById(serviceOfferedId).orElseThrow(
                () -> new IllegalStateException("Service does not exist")
        );

        serviceOfferedMapper.updateServiceOfferedFromDTO(serviceOfferedDTO, existingServiceOffered);

        if(file != null && !file.isEmpty()){
            imageService.deleteImage(existingServiceOffered.getImage());
            String fileName = imageService.saveImage(file);
            existingServiceOffered.setImage(fileName);
        }

        ServiceOffered updatedServiceOffered = serviceOfferedRepository.save(existingServiceOffered);
        return serviceOfferedMapper.toServiceOfferedDTO(updatedServiceOffered);
    }

    public String deleteServiceOffered(Long serviceOfferedId){
        ServiceOffered serviceOffered = serviceOfferedRepository.findById(serviceOfferedId).orElseThrow(
                () -> new IllegalStateException("Service does not exist")
        );
        imageService.deleteImage(serviceOffered.getImage());
        serviceOfferedRepository.deleteById(serviceOfferedId);
        return "Product with id: " + serviceOfferedId + " has been deleted";
    }
}
