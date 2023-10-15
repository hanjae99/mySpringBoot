package com.keduit.controller;

import com.keduit.dto.ResponseDTO;
import com.keduit.dto.TodoDTO;
import com.keduit.model.TodoEntity;
import com.keduit.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = todoService.testService();
        List<String> list = new ArrayList<>();

        list.add(str);

        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder()
                .data(list)
                .error("에러 입니당")
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO,
                                        @AuthenticationPrincipal String userId){
        try {

            TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
            todoEntity.setId(null);
            todoEntity.setUserId(userId);

            // 서비스로부터 EntityList 가져옴
            List<TodoEntity> todoEntityList = todoService.create(todoEntity);
            // 리스트에 있는 각 entity 들을 dto 로 변환 (스트림 활용)
            List<TodoDTO> dtos = todoEntityList.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String errorMsg = e.getMessage();

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(errorMsg).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO,
                                        @AuthenticationPrincipal String userId){

        TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
        todoEntity.setUserId(userId);

        List<TodoEntity> todoEntityList = todoService.update(todoEntity);
        List<TodoDTO> dtos = todoEntityList.stream()
                .map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO,
                                        @AuthenticationPrincipal String userId){
        try {

            TodoEntity entity = TodoDTO.toEntity(todoDTO);
            entity.setUserId(userId);
            List<TodoEntity> todoEntityList = todoService.delete(entity);
            List<TodoDTO> todoDTOList = todoEntityList.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(todoDTOList).build();
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            String errorMsg = e.getMessage();

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(errorMsg).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping
    public ResponseEntity<?> selectTodo(@AuthenticationPrincipal String userId){
        try {

            List<TodoEntity> todoEntityList = todoService.read(userId);
            List<TodoDTO> todoDTOList = todoEntityList.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(todoDTOList).build();
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            String errorMsg = e.getMessage();

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(errorMsg).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
