package com.khoahd7621.youngblack.services;

import com.khoahd7621.youngblack.dtos.request.color.CreateNewColorRequest;
import com.khoahd7621.youngblack.dtos.request.color.UpdateColorNameRequest;
import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.dtos.response.color.ColorResponse;
import com.khoahd7621.youngblack.dtos.response.color.ListColorsResponse;
import com.khoahd7621.youngblack.exceptions.custom.BadRequestException;

public interface ColorAdminService {
    public SuccessResponse<ColorResponse> createNewColor(CreateNewColorRequest createNewColorRequest) throws BadRequestException;

    public SuccessResponse<ListColorsResponse> getAllColors();

    public SuccessResponse<ColorResponse> updateColorName(UpdateColorNameRequest updateColorNameRequest) throws BadRequestException;
}
