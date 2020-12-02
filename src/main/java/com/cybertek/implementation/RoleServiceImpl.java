package com.cybertek.implementation;

import com.cybertek.dto.RoleDTO;
import com.cybertek.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractMapService<RoleDTO, Long> implements RoleService {
    @Override
    public List<RoleDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(RoleDTO t) {
        super.delete(t);
    }

    @Override
    public void update(RoleDTO object) {
        super.update(object.getId(), object);
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        return super.save(roleDTO.getId(), roleDTO);
    }

    @Override
    public RoleDTO findById(Long id) {
        return super.findById(id);
    }
}
