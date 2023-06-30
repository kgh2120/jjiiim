package com.kk.jjiiim.service;


import com.kk.jjiiim.domain.Manager;
import com.kk.jjiiim.domain.Store;
import com.kk.jjiiim.domain.User;
import com.kk.jjiiim.dto.RegisterStore;
import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.exception.ManagerApiException;
import com.kk.jjiiim.repository.ManagerRepository;
import com.kk.jjiiim.repository.StoreRepository;
import com.kk.jjiiim.repository.UserRepository;
import com.kk.jjiiim.util.SignUpValidator;
import com.kk.jjiiim.util.UserCreateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final StoreRepository storeRepository;

    public void signUp(SignUp.Request dto) {
        SignUpValidator.validateSignUp(userRepository,dto);
        managerRepository.save(UserCreateFactory.createManager(dto,passwordEncoder));
    }

    public void registerStore(RegisterStore.Request dto) {
        validateRegisterStore(dto);
        saveStoreEntity(dto);
    }

    private void saveStoreEntity(RegisterStore.Request dto) {
        Store store = Store.builder()
                .address(dto.getAddress())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .name(dto.getName())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
        store.associatedWithManager(getManager());
        storeRepository.save(store);
    }

    private void validateRegisterStore(RegisterStore.Request dto){
        if (storeRepository.existsByName(dto.getName()))
            throw ManagerApiException.alreadyRegisteredStoreName();
    }

    private Manager getManager(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return managerRepository.findById(user.getId())
                .orElseThrow();
    }
}
