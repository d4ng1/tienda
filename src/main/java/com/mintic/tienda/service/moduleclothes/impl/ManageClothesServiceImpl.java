package com.mintic.tienda.service.moduleclothes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mintic.tienda.domain.moduleclothes.exceptions.ManageClothesDomainException;
import com.mintic.tienda.domain.moduleclothes.impl.ManageClothesDomainImpl;
import com.mintic.tienda.entity.Prenda;
import com.mintic.tienda.mapper.IPrendaMapper;
import com.mintic.tienda.mapper.impl.PrendaMapperImpl;
import com.mintic.tienda.repository.IPrendaRepo;
import com.mintic.tienda.service.DTO.PrendaDTO;
import com.mintic.tienda.service.moduleclothes.IManageClothesService;
import com.mintic.tienda.service.moduleclothes.exceptions.ManagerClothesServiceException;

@Service
public class ManageClothesServiceImpl implements IManageClothesService {

    
    private final IPrendaRepo prendaRepo;

    public ManageClothesServiceImpl(IPrendaRepo prendaRepo) {
        this.prendaRepo = prendaRepo;
    }

    private final IPrendaMapper prendaMapper = new PrendaMapperImpl();


    @Override
    public PrendaDTO savePrenda(PrendaDTO prendaDTO) throws ManagerClothesServiceException, ManageClothesDomainException {
        
        if(prendaDTO == null) {
            throw new ManagerClothesServiceException(ManagerClothesServiceException.PRENDA_NULL);
        }    

        ManageClothesDomainImpl manageClothesDomainImpl = new ManageClothesDomainImpl();
        manageClothesDomainImpl.validatePrenda(prendaDTO);
        
        Prenda prenda = prendaMapper.dTOToEntity(prendaDTO);
        prenda = prendaRepo.save(prenda);
        return prendaMapper.entityToDTO(prenda);
    }

    @Override
    public List<PrendaDTO> getAllPrendas() {
        return prendaRepo.findAllByOrderByIdDesc().stream().map(prendaMapper::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public PrendaDTO getOnePrenda(Long idPrenda) throws ManagerClothesServiceException {
        try {
            return prendaMapper.entityToDTO(prendaRepo.findById(idPrenda).orElseThrow());
        } catch (Exception e) {
            throw new ManagerClothesServiceException(ManagerClothesServiceException.IDPRENDA_NULL);
        }
    }

    @Override
    public void deletePrendaById(Long idPrenda) {
        prendaRepo.deleteById(idPrenda);
    }

}
