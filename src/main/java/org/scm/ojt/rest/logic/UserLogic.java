package org.scm.ojt.rest.logic;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.scm.ojt.rest.dao.ConnectionManager;
import org.scm.ojt.rest.dao.SupplierDAO;
import org.scm.ojt.rest.dto.SupplierDTO;
import org.scm.ojt.rest.dto.UserDTO;
import org.scm.ojt.rest.entity.Supplier;
import org.scm.ojt.rest.entity.User;
import org.scm.ojt.rest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 17/12/2018.
 */
public class UserLogic {
    private static final Logger LOG = LoggerFactory.getLogger(UserLogic.class);

    private final ConnectionManager connectionManager;
    private final ModelMapper modelMapper;

    private UserRepository userRepository;

    @Inject
    public UserLogic(ConnectionManager connectionManager,ModelMapper modelMapper){
        this.connectionManager = connectionManager;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.userRepository = new UserRepository(connectionManager.getSessionFactoryObj());
    }

    public List<UserDTO> listAll(){
        Iterable<User> users = userRepository.findAll();
        return createUserDTOList(users);
    }

    public List<UserDTO> createUserDTOList(Iterable<User> users){
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for (User user : users) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public UserDTO findById(Integer id){
        UserDTO userDTO = modelMapper.map(userRepository.findById(id), UserDTO.class);
        return userDTO;
    }

    public UserDTO create(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        UserDTO userResult = modelMapper.map(userRepository.create(user), UserDTO.class);
        return userResult;
    }
}
