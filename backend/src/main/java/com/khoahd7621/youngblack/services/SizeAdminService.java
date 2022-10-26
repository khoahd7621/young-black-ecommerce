package com.khoahd7621.youngblack.services;

import com.khoahd7621.youngblack.dtos.request.size.CreateNewSizeRequest;
import com.khoahd7621.youngblack.dtos.request.size.UpdateSizeRequest;
import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.dtos.response.size.ListSizesResponse;
import com.khoahd7621.youngblack.dtos.response.size.SizeResponse;
import com.khoahd7621.youngblack.exceptions.custom.CustomBadRequestException;

public interface SizeAdminService {
    public SuccessResponse<SizeResponse> createNewSize(CreateNewSizeRequest createNewSizeRequest) throws CustomBadRequestException;

    public SuccessResponse<ListSizesResponse> getAllSize();

    public SuccessResponse<SizeResponse> updateSize(UpdateSizeRequest updateSizeRequest) throws CustomBadRequestException;
}