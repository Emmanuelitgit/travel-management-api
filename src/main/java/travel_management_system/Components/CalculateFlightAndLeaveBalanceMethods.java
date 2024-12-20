package travel_management_system.Components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Repositories.EligibilityRepository;
import travel_management_system.Repositories.FlightAndLeaveBalanceRepository;
import travel_management_system.Repositories.LeaveRequestRepository;
import travel_management_system.Repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Optional;

@Slf4j
@Component
@EnableScheduling
public class CalculateFlightAndLeaveBalanceMethods {
    private final UserRepository userRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EligibilityRepository eligibilityRepository;
    private final FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository;

    @Autowired
    public CalculateFlightAndLeaveBalanceMethods(EligibilityRepository eligibilityRepository, UserRepository userRepository, LeaveRequestRepository leaveRequestRepository, FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository) {
        this.eligibilityRepository = eligibilityRepository;
        this.userRepository = userRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.flightAndLeaveBalanceRepository = flightAndLeaveBalanceRepository;
    }



    public FlightAndLeaveBalance calculateFlightAndLeaveBalance(Long leaveRequestId){

        FlightAndLeaveBalance leaveBalanceUsingExistingLeaveRequestData = calculateUserLeaveAndFlightBalanceWithExistingRequestData(leaveRequestId);
        if (leaveBalanceUsingExistingLeaveRequestData == null){
            LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElse(null);
            if (leaveRequest == null){
                throw new NotFoundException("leave request not found");
            }

            FlightAndLeaveBalance userBalance = flightAndLeaveBalanceRepository
                    .findFlightAndLeaveBalanceByUserId(leaveRequest.getUser().getId()).orElse(null);
            log.info("user balance:{}", userBalance);
            if (userBalance == null){
                throw new NotFoundException("user balance not found");
            }

            double userAccumulatedLeaveDays = ((double) userBalance.getAccumulated_working_days() /90)*14;
            double userAccumulatedFlight = userAccumulatedLeaveDays/14;
            double leaveBalance = userAccumulatedLeaveDays - leaveRequest.getLeave_days();
            double roundedLeaveBalance = Math.round(leaveBalance * 10.0)/10.0;
            double flightBalance = userAccumulatedFlight-1.0;
            double roundedFlightBalance = Math.round(flightBalance * 10.0)/10.0;

            if (flightBalance < 0 || leaveBalance < 0){
                throw new NotFoundException("Insufficient balance to complete the operation");
            }
            userBalance.setLeave_balance(roundedLeaveBalance);
            userBalance.setFlight_balance(roundedFlightBalance);
            log.info("flight balance: ========={}",String.valueOf(flightBalance));
            flightAndLeaveBalanceRepository.save(userBalance);
            return userBalance;
        }

        return leaveBalanceUsingExistingLeaveRequestData;
    }


    public FlightAndLeaveBalance calculateUserLeaveAndFlightBalanceWithExistingRequestData(Long leaveRequestId){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElse(null);
        Optional<Date> userExistingRequestLatestArrivalDate = leaveRequestRepository
                .getUserLatestArrivalByUserId(leaveRequest.getUser().getId());
        if (userExistingRequestLatestArrivalDate.isPresent()){
            LocalDate userArrival = userExistingRequestLatestArrivalDate.get().toLocalDate();
            long userAccumulatedWorkingDays = ChronoUnit.DAYS.between(userArrival, LocalDate.now());
            log.info("userAccumulatedWorkingDays:{}", userAccumulatedWorkingDays);
            double accumulatedLeaveBalance = ((double) userAccumulatedWorkingDays /90)*14;
            double accumulatedFlightBalance = accumulatedLeaveBalance/14;
            log.info("accumulate flight:{}", accumulatedFlightBalance);

            FlightAndLeaveBalance userBalance = flightAndLeaveBalanceRepository
                    .findFlightAndLeaveBalanceByUserId(leaveRequest.getUser().getId()).orElse(null);
            double leaveBalance = (userBalance.getLeave_balance() +  accumulatedLeaveBalance) - leaveRequest.getLeave_days();
            double flightBalance = (userBalance.getFlight_balance() + accumulatedFlightBalance) - 1.0;
            double roundedLeaveBalanceToOneDecimalPlace = Math.round(leaveBalance * 10.0)/10.0;
            double roundedFlightBalanceToOneDecimalPlace = Math.round(flightBalance * 10.0)/10.0;

            if (roundedFlightBalanceToOneDecimalPlace < 0 || roundedLeaveBalanceToOneDecimalPlace < 0){
                throw new NotFoundException("Insufficient balance to complete the operation");
            }

            userBalance.setLeave_balance(roundedLeaveBalanceToOneDecimalPlace);
            userBalance.setFlight_balance(roundedFlightBalanceToOneDecimalPlace);
            userBalance.setAccumulated_working_days(userBalance.getAccumulated_working_days() + userAccumulatedWorkingDays);
            flightAndLeaveBalanceRepository.save(userBalance);
            return userBalance;
        }
        return null;
    }

//
//    @Async
//    @Scheduled(cron = "*/1 * * * * *")
//    public  void calculateAccumulatedWorkingDays(){
//        flightAndLeaveBalanceRepository.calculateAccumulatedLeaveDays();
//    }

    public long calculateLeaveRequestingDays(Date departure, Date arrival) {
        LocalDate departureLocalDate = departure.toLocalDate();
        LocalDate arrivalLocalDate = arrival.toLocalDate();
        return ChronoUnit.DAYS.between(departureLocalDate, arrivalLocalDate);
    }
}