package com.example.security.controller;

import com.example.security.domain.User;
import com.example.security.domain.UserRepository;
import com.example.security.swagger.result.CommonResult;
import com.example.security.swagger.result.ListResult;
import com.example.security.swagger.result.SingleResult;
import com.example.security.swagger.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping("/user")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping("/user/{msrl}")
    public SingleResult<User> findUserById(
            @ApiParam(value = "회원 ID", required = true) @PathVariable Long msrl) throws Exception {
        return responseService.getSingleResult(userRepository.findById(msrl).orElseThrow(Exception::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })


    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping("/user")
    public SingleResult<User> save(
            @ApiParam(value = "회원정보", required = true) @RequestBody User resource) {
        User user = User.builder()
                .uid(resource.getUid())
                .name(resource.getName())
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 정보", required = true) @RequestBody User resource) {

        User user = User.builder()
                .msrl(resource.getMsrl())
                .uid(resource.getUid())
                .name(resource.getName())
                .build();

        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원 번호", required = true) @PathVariable Long msrl) {

        userRepository.deleteById(msrl);
        return responseService.getSuccessResult();
    }



}