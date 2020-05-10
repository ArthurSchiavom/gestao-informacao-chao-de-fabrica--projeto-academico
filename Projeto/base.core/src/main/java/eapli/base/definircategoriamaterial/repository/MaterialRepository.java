package eapli.base.definircategoriamaterial.repository;

import eapli.base.definircategoriamaterial.domain.Material;

import java.util.List;

public interface MaterialRepository {
    List<Material> findAllList();
}
