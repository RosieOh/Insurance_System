package com.insurance.grpc;

import com.insurance.domain.contract.entity.Contract;
import com.insurance.domain.contract.service.ContractService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ContractGrpcService extends ContractServiceGrpc.ContractServiceImplBase {

    @Autowired
    private ContractService contractService;

    @Override
    public void getContractById(ContractIdRequest request, StreamObserver<ContractDetailResponse> responseObserver) {
        ContractDetailResponse.Builder responseBuilder = ContractDetailResponse.newBuilder();
        contractService.getContract(request.getContractId()).ifPresentOrElse(contract -> {
            responseBuilder
                .setSuccess(true)
                .setMessage("success")
                .setId(contract.getId())
                .setCustomerName(contract.getCustomerName())
                .setProductName(contract.getProductName())
                .setStartDate(contract.getStartDate() != null ? contract.getStartDate().toString() : "")
                .setEndDate(contract.getEndDate() != null ? contract.getEndDate().toString() : "");
        }, () -> {
            responseBuilder
                .setSuccess(false)
                .setMessage("계약을 찾을 수 없습니다.");
        });
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
} 