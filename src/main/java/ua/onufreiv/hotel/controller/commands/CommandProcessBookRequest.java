package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IUserService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;
import ua.onufreiv.hotel.service.impl.UserService;
import ua.onufreiv.hotel.util.roomfinder.AlternativeRoomFinder;
import ua.onufreiv.hotel.util.roomfinder.ExactRoomChooser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandProcessBookRequest implements Command {
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;
    private final IBookRequestService bookRequestService;
    private final IUserService userService;
    private final JspConfig jspConfig;

    public CommandProcessBookRequest() {
        bookRequestService = BookRequestService.getInstance();
        userService = UserService.getInstance();
        roomService = RoomService.getInstance();
        roomTypeService = RoomTypeService.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(jspConfig.get(BOOK_REQUEST_ID_NAME)));

        BookRequest bookRequest = bookRequestService.findById(bookRequestId);
        User user = userService.findById(bookRequest.getUserId());
        Room exactRoom = roomService.searchRoomForRequest(bookRequest, new ExactRoomChooser());
        Room alternateRoom = roomService.searchRoomForRequest(bookRequest, new AlternativeRoomFinder());

        request.setAttribute(jspConfig.get(USER_NAME), user);
        request.setAttribute(jspConfig.get(BOOK_REQUEST_NAME), bookRequest);
        request.setAttribute(jspConfig.get(EXACT_ROOM_NAME), exactRoom);
        request.setAttribute(jspConfig.get(ALTERNATIVE_ROOM_NAME), alternateRoom);
        request.setAttribute(jspConfig.get(ID_ROOM_TYPE_MAP_NAME), roomTypeService.findAllAsMap());

        return PathConfig.getInstance().getProperty(PathConfig.PROCESS_REQUEST_PAGE_PATH);
    }
}