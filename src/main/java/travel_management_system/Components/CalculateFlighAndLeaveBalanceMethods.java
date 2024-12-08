package travel_management_system.Components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import travel_management_system.Models.Eligibility;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Models.User;
import travel_management_system.Repositories.EligibilityRepository;
import travel_management_system.Repositories.FlightAndLeaveBalanceRepository;
import travel_management_system.Repositories.LeaveRequestRepository;
import travel_management_system.Repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Slf4j
@Component
public class CalculateFlighAndLeaveBalanceMethods {
    private final UserRepository userRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EligibilityRepository eligibilityRepository;
    private final FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository;

    @Autowired
    public CalculateFlighAndLeaveBalanceMethods(EligibilityRepository eligibilityRepository, UserRepository userRepository, LeaveRequestRepository leaveRequestRepository, FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository) {
        this.eligibilityRepository = eligibilityRepository;
        this.userRepository = userRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.flightAndLeaveBalanceRepository = flightAndLeaveBalanceRepository;
    }

    /**
     * @author -> Emmanuel Yidana
     * @param -> leave_request_id
     * @return -> flightBalance
     * @description -> a method to calculate leave balance
     * @date -> 08-12-2024
     */
    public double calculateLeaveBalance(Long leave_request_id){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leave_request_id).orElse(null);
        FlightAndLeaveBalance userBalance = flightAndLeaveBalanceRepository.findById(leaveRequest.getUser().getId()).orElse(null);

        double userAccumulatedLeaveDays = ((double) userBalance.getAccumulated_working_days() /90)*14;
        double leaveBalance = userAccumulatedLeaveDays - leaveRequest.getLeave_days();
        userBalance.setLeave_balance((int)leaveBalance);
        flightAndLeaveBalanceRepository.save(userBalance);
        log.info("leave days requesting: ========{}",String.valueOf(leaveRequest.getLeave_days()));
        log.info("user accumulated leave days: ========{}",String.valueOf(userAccumulatedLeaveDays));
        log.info("leave balance: ========{}", String.valueOf(leaveBalance));
        return leaveBalance;
    }

    /**
     * @author -> Emmanuel Yidana
     * @param -> leave_request_id
     * @return -> flightBalance
     * @description -> a method to calculate flight balance
     * @date -> 08-12-2024
     */
    public double  calculateFlightBalance(Long leave_request_id){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leave_request_id).orElse(null);
        FlightAndLeaveBalance userBalance = flightAndLeaveBalanceRepository.findById(leaveRequest.getUser().getId()).orElse(null);

        double userAccumulatedLeaveDays = ((double) userBalance.getAccumulated_working_days() /90)*14;
            double userAccumulatedFlight = userAccumulatedLeaveDays/14;
            double flightBalance = userAccumulatedFlight-1.0;
            userBalance.setFlight_balance((int)flightBalance);
            log.info("flight balance: ========={}",String.valueOf(flightBalance));
            flightAndLeaveBalanceRepository.save(userBalance);
            return flightBalance;
    }

    /**
     * @param departure
     * @param arrival
     * @return number of leave days requesting for
     * @desecription -> a method to calculate a number a leave days requesting for
     */
    public long calculateLeaveRequestingDays(Date departure, Date arrival) {
        LocalDate departureLocalDate = departure.toLocalDate();
        LocalDate arrivalLocalDate = arrival.toLocalDate();
        return ChronoUnit.DAYS.between(departureLocalDate, arrivalLocalDate);
    }
}