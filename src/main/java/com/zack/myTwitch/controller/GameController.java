package com.zack.myTwitch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zack.myTwitch.service.GameService;
import com.zack.myTwitch.service.TwitchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class GameController {
    // /game?game_name=whatever
    // /game

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public void getGame(
            @RequestParam(value = "game_name", required = false)
                    String gameName, HttpServletResponse response
            ) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL, otherwise return the top x games.
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }
    }
}
