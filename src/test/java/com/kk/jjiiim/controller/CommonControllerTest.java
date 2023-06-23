package com.kk.jjiiim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.jjiiim.config.SecurityConfig;
import com.kk.jjiiim.dto.CheckId;
import com.kk.jjiiim.exception.CommonErrorCode;
import com.kk.jjiiim.service.CommonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {CommonController.class, SecurityConfig.class})
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost/docs", uriPort = 8080)
class CommonControllerTest {

    public static final String IS_DUPLICATE_ID = "/common/id";
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CommonController commonController;

    @MockBean
    private CommonService commonService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("아이디 중복 검사 - [성공] 사용 가능")
    @Test
    void isDuplicatedId_SUCCESS_FALSE () throws Exception{
        //given

        CheckId.Request request = new CheckId.Request("foobar");
        given(commonService.isDuplicatedId(request))
                .willReturn(new CheckId.Response(false));
        //when
        mockMvc.perform(post(IS_DUPLICATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andDo(document("/common/check-id/false",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        ))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false));
    }

    @DisplayName("아이디 중복 검사 - [성공] 사용 불가")
    @Test
    void isDuplicatedId_SUCCESS_TRUE () throws Exception{
        //given
        CheckId.Request request = new CheckId.Request("foobar");
        given(commonService.isDuplicatedId(request))
                .willReturn(new CheckId.Response(true));
        //when
        mockMvc.perform(post(IS_DUPLICATE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andDo(document("/common/check-id/true",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                ))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }
    @DisplayName("아이디 중복 검사 - [실패] ID 공백 입력")
    @Test
    void isDuplicatedId_FAIL_ID_BLANK () throws Exception{
        //given
        CheckId.Request request = new CheckId.Request("");
        CommonErrorCode occuredError = CommonErrorCode.INVALID_INPUT;
        //when
        mockMvc.perform(post(IS_DUPLICATE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(occuredError.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(occuredError.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(occuredError.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(IS_DUPLICATE_ID));
    }
    @DisplayName("아이디 중복 검사 - [실패] ID 입력 안함")
    @Test
    void isDuplicatedId_FAIL_ID_NULL () throws Exception{
        //given
        CheckId.Request request = new CheckId.Request(null);
        CommonErrorCode occuredError = CommonErrorCode.INVALID_INPUT;
        //when
        mockMvc.perform(post(IS_DUPLICATE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(occuredError.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(occuredError.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(occuredError.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(IS_DUPLICATE_ID));
    }

}