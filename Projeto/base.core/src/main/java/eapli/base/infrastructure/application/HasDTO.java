package eapli.base.infrastructure.application;

import eapli.base.definircategoriamaterial.domain.MaterialDTO;

import java.util.List;

public interface HasDTO<E> {
    E toDTO();
}
