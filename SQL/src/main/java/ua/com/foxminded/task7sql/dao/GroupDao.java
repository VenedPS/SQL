package ua.com.foxminded.task7sql.dao;

import java.util.List;

import ua.com.foxminded.task7sql.dto.Group;

public interface GroupDao {
    public String addGroup(Group input);
    public Group getGroupById(int input);
    public List<Group> searchGroupsByStudentsQuantity(int quantity);
}
