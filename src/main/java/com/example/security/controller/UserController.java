package com.example.security.controller;

import com.example.security.controller.exception.custom.CUserNotFoundException;
import com.example.security.domain.User;
import com.example.security.domain.UserRepository;
import com.example.security.swagger.result.CommonResult;
import com.example.security.swagger.result.ListResult;
import com.example.security.swagger.result.SingleResult;
import com.example.security.swagger.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userRepository.findByUid(id).orElseThrow(CUserNotFoundException::new));
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
//    @PostMapping("/user")
//    public SingleResult<User> save(
//            @ApiParam(value = "회원정보", required = true) @RequestBody User resource) {
//        User user = User.builder()
//                .uid(resource.getUid())
//                .name(resource.getName())
//                .build();
//        return responseService.getSingleResult(userRepository.save(user));
//    }




    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
//    @PutMapping(value = "/user")
//    public SingleResult<User> modify(
//            @ApiParam(value = "회원 정보", required = true) @RequestBody User resource) {
//
//        User user = User.builder()
//                .msrl(resource.getMsrl())
//                .uid(resource.getUid())
//                .name(resource.getName())
//                .build();
//
//        return responseService.getSingleResult(userRepository.save(user));
//    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam int msrl,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(Long.valueOf(msrl))
                .name(name)
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