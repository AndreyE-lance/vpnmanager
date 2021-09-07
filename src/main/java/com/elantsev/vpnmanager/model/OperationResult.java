package com.elantsev.vpnmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationResult {
    private boolean isSuccess;
    private String message;
    private boolean isVisible;
}
