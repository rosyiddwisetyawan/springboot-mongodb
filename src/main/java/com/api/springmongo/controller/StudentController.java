package com.api.springmongo.controller;

import com.api.springmongo.dto.StudentDTO;
import com.api.springmongo.model.Response;
import com.api.springmongo.model.Student;
import com.api.springmongo.service.StudentService;
import com.api.springmongo.service.StudentServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.core.convert.TypeDescriptor.map;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/")
    public List<StudentDTO> getAllStudents() {

        List<Student> studentList = studentService.findAll();
        List<StudentDTO> res = studentList.stream()
                .map(e -> new StudentDTO(e.getId(), e.getName(), e.getStudentNumber(), e.getEmail(), e.getCourseList(), e.getGpa()))
                .collect(Collectors.toList());
        return res;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> saveOrUpdateStudent(@RequestBody StudentDTO studentDTO) {
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.convertValue(studentDTO, Student.class);
        Student st = studentService.saveOrUpdateStudent(student);
        Response res = new Response();
        if(st!=null){
            res.setStatus(true);
            res.setMessage("Student added successfully");
        }else {
            res.setStatus(false);
            res.setMessage("Student added failed");
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{email}/{studentNumber}")
    public List<StudentDTO> getAllStudents(@PathVariable String email, @PathVariable long studentNumber) {

        List<Student> studentList = studentService.findByEmailAndStudentNumber(email,studentNumber);
        List<StudentDTO> res = studentList.stream()
                .map(e -> new StudentDTO(e.getId(), e.getName(), e.getStudentNumber(), e.getEmail(), e.getCourseList(), e.getGpa()))
                .collect(Collectors.toList());
        return res;
    }

    @GetMapping(value = "/get/{email}")
    public List<StudentDTO> getAllByQuery(@PathVariable String email){
        List<Student> studentList = studentService.findByQuery(email);
        List<StudentDTO> res = studentList.stream()
                .map(e -> new StudentDTO(e.getId(), e.getName(), e.getStudentNumber(), e.getEmail(), e.getCourseList(), e.getGpa()))
                .collect(Collectors.toList());
        return res;
    }

    @PostMapping(value = "/postfitruums")
    public String getfit() {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("test_res_fit.txt");

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line=null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                return line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @PostMapping(value = "/xml", produces = MediaType.TEXT_XML_VALUE)
    public String mockxml() {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><GetBookingResponse xmlns=\"http://tempuri.org/\"><GetBookingResult xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><NSProcessTime>329</NSProcessTime><ProcessTime>337</ProcessTime><State>Clean</State><RecordLocator>YPTMNM</RecordLocator><CurrencyCode>IDR</CurrencyCode><PaxCount>1</PaxCount><SystemCode/><BookingID>368726851</BookingID><BookingParentID>0</BookingParentID><ParentRecordLocator/><BookingChangeCode/><GroupName/><BookingInfo><State>Clean</State><BookingStatus>Hold</BookingStatus><BookingType/><ChannelType>API</ChannelType><CreatedDate>2024-03-15T04:03:31.503Z</CreatedDate><ExpiredDate>2024-03-24T22:10:00Z</ExpiredDate><ModifiedDate>2024-03-15T04:03:31.503Z</ModifiedDate><PriceStatus>Valid</PriceStatus><ProfileStatus>KnownIndividual</ProfileStatus><ChangeAllowed>true</ChangeAllowed><CreatedAgentID>16978222</CreatedAgentID><ModifiedAgentID>16978222</ModifiedAgentID><BookingDate>2024-03-15T04:03:31.503Z</BookingDate><OwningCarrierCode>QZ</OwningCarrierCode><PaidStatus>UnderPaid</PaidStatus></BookingInfo><POS><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode></POS><SourcePOS><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode></SourcePOS><TypeOfSale><State>Clean</State><PromotionCode/><PaxResidentCountry>ID</PaxResidentCountry></TypeOfSale><BookingHold><State>Clean</State><HoldDateTime>2024-03-15T06:03:31Z</HoldDateTime></BookingHold><BookingSum><BalanceDue>738083.0000</BalanceDue><AuthorizedBalanceDue>738083.0000</AuthorizedBalanceDue><SegmentCount>1</SegmentCount><PassiveSegmentCount>0</PassiveSegmentCount><TotalCost>738083.0000</TotalCost><PointsBalanceDue>0</PointsBalanceDue><TotalPointCost>0</TotalPointCost><AlternateCurrencyCode/><AlternateCurrencyBalanceDue>0</AlternateCurrencyBalanceDue></BookingSum><ReceivedBy><State>Clean</State><ReceivedBy/><ReceivedReference/><ReferralCode/><LatestReceivedBy/><LatestReceivedReference/></ReceivedBy><Passengers><Passenger><State>Clean</State><CustomerNumber/><PassengerNumber>0</PassengerNumber><FamilyNumber>0</FamilyNumber><PaxDiscountCode/><Names><BookingName><State>Clean</State><FirstName>Rosyid</FirstName><MiddleName/><LastName>Setyawan</LastName><Suffix/><Title>MR</Title></BookingName></Names><PassengerInfo><State>Clean</State><BalanceDue>738083.0000</BalanceDue><Gender>Male</Gender><Nationality>ID</Nationality><ResidentCountry>ID</ResidentCountry><TotalCost>738083.0000</TotalCost><WeightCategory>Male</WeightCategory></PassengerInfo><PassengerProgram><State>Clean</State><ProgramCode/><ProgramLevelCode/><ProgramNumber/></PassengerProgram><PassengerAddresses><PassengerAddress><State>Clean</State><TypeCode>E</TypeCode><StationCode>   </StationCode><CompanyName>NUSATRIP</CompanyName><AddressLine1>KH. ABDULLAH SYAFE'I KAV. 27</AddressLine1><AddressLine2/><AddressLine3/><City/><ProvinceState/><PostalCode/><CountryCode/><Phone>+6287888881234</Phone><EmailAddress>airdoc_TEJ4KX@doc.nusatrip.com</EmailAddress></PassengerAddress></PassengerAddresses><PassengerTravelDocuments><PassengerTravelDocument><State>Clean</State><DocTypeCode>IC</DocTypeCode><IssuedByCode>ID</IssuedByCode><DocSuffix> </DocSuffix><DocNumber>1234567890123456</DocNumber><DOB>1995-12-08T00:00:00</DOB><Gender>Male</Gender><Nationality>ID</Nationality><ExpirationDate>9999-12-31T00:00:00Z</ExpirationDate><Names><BookingName><State>Clean</State><FirstName>ROSYID</FirstName><MiddleName/><LastName>SETYAWAN</LastName><Suffix/><Title>MR</Title></BookingName></Names><BirthCountry>ID</BirthCountry><IssuedDate>9999-12-31T00:00:00Z</IssuedDate></PassengerTravelDocument></PassengerTravelDocuments><PassengerID>794022783</PassengerID><PassengerTypeInfos><PassengerTypeInfo><State>Clean</State><DOB>1995-12-08T00:00:00</DOB><PaxType>ADT</PaxType></PassengerTypeInfo></PassengerTypeInfos><PassengerInfos><PassengerInfo><State>Clean</State><BalanceDue>738083.0000</BalanceDue><Gender>Male</Gender><Nationality>ID</Nationality><ResidentCountry>ID</ResidentCountry><TotalCost>738083.0000</TotalCost><WeightCategory>Male</WeightCategory></PassengerInfo></PassengerInfos><PseudoPassenger>false</PseudoPassenger><PassengerTypeInfo><State>Clean</State><DOB>1995-12-08T00:00:00</DOB><PaxType>ADT</PaxType></PassengerTypeInfo></Passenger></Passengers><Journeys><Journey><State>Clean</State><NotForGeneralUse>false</NotForGeneralUse><Segments><Segment><State>Clean</State><ActionStatusCode>HK</ActionStatusCode><ArrivalStation>DPS</ArrivalStation><CabinOfService>Y</CabinOfService><ChangeReasonCode/><DepartureStation>CGK</DepartureStation><PriorityCode/><SegmentType/><STA>2024-03-24T08:05:00</STA><STD>2024-03-24T05:10:00</STD><International>false</International><FlightDesignator><State>New</State><CarrierCode>QZ</CarrierCode><FlightNumber>7510</FlightNumber><OpSuffix> </OpSuffix></FlightDesignator><Fares><Fare><State>Clean</State><ClassOfService>K</ClassOfService><ClassType> </ClassType><RuleTariff/><CarrierCode>AK</CarrierCode><RuleNumber>AAB1</RuleNumber><FareBasisCode>K01H15</FareBasisCode><FareSequence>2</FareSequence><FareClassOfService>K</FareClassOfService><FareStatus>Default</FareStatus><FareApplicationType>Route</FareApplicationType><OriginalClassOfService>K</OriginalClassOfService><XrefClassOfService/><PaxFares><PaxFare><State>Clean</State><PaxType>ADT</PaxType><PaxDiscountCode/><FareDiscountCode/><ServiceCharges><BookingServiceCharge><ChargeType>FarePrice</ChargeType><CollectType>SellerChargeable</CollectType><ChargeCode/><TicketCode/><CurrencyCode>IDR</CurrencyCode><Amount>441723.0000</Amount><ChargeDetail/><ForeignCurrencyCode>IDR</ForeignCurrencyCode><ForeignAmount>441723.0000</ForeignAmount><BaseAmount>0</BaseAmount></BookingServiceCharge><BookingServiceCharge><ChargeType>TravelFee</ChargeType><CollectType>SellerChargeable</CollectType><ChargeCode>COS</ChargeCode><TicketCode>COS</TicketCode><CurrencyCode>IDR</CurrencyCode><Amount>7000.0000</Amount><ChargeDetail>CGK-DPS</ChargeDetail><ForeignCurrencyCode>IDR</ForeignCurrencyCode><ForeignAmount>7000.0000</ForeignAmount><BaseAmount>0</BaseAmount></BookingServiceCharge><BookingServiceCharge><ChargeType>TravelFee</ChargeType><CollectType>SellerChargeable</CollectType><ChargeCode>FUEL</ChargeCode><TicketCode>FUE</TicketCode><CurrencyCode>IDR</CurrencyCode><Amount>121622.0000</Amount><ChargeDetail>CGK-DPS</ChargeDetail><ForeignCurrencyCode>IDR</ForeignCurrencyCode><ForeignAmount>121622.0000</ForeignAmount><BaseAmount>0</BaseAmount></BookingServiceCharge><BookingServiceCharge><ChargeType>TravelFee</ChargeType><CollectType>SellerChargeable</CollectType><ChargeCode>PSCH</ChargeCode><TicketCode>PSH</TicketCode><CurrencyCode>IDR</CurrencyCode><Amount>100000.0000</Amount><ChargeDetail>CGK-DPS</ChargeDetail><ForeignCurrencyCode>IDR</ForeignCurrencyCode><ForeignAmount>100000.0000</ForeignAmount><BaseAmount>0</BaseAmount></BookingServiceCharge><BookingServiceCharge><ChargeType>TravelFee</ChargeType><CollectType>SellerChargeable</CollectType><ChargeCode>IWJR</ChargeCode><TicketCode>IWR</TicketCode><CurrencyCode>IDR</CurrencyCode><Amount>5000.0000</Amount><ChargeDetail>CGK-DPS</ChargeDetail><ForeignCurrencyCode>IDR</ForeignCurrencyCode><ForeignAmount>5000.0000</ForeignAmount><BaseAmount>0</BaseAmount></BookingServiceCharge><BookingServiceCharge><ChargeType>Tax</ChargeType><CollectType>SellerChargeable</CollectType><ChargeCode>VAT</ChargeCode><TicketCode>VAT</TicketCode><CurrencyCode>IDR</CurrencyCode><Amount>62738.0000</Amount><ChargeDetail>CGK-DPS</ChargeDetail><ForeignCurrencyCode>IDR</ForeignCurrencyCode><ForeignAmount>62738.0000</ForeignAmount><BaseAmount>0</BaseAmount></BookingServiceCharge></ServiceCharges></PaxFare></PaxFares><ProductClass>EP</ProductClass><IsAllotmentMarketFare>false</IsAllotmentMarketFare><TravelClassCode>Y</TravelClassCode><FareSellKey>0~K~ ~AK~K01H15~AAB1~~1~2~NCGKDPS0010020~X</FareSellKey><InboundOutbound>None</InboundOutbound><AvailableCount>0</AvailableCount><Status>Active</Status><FareLink>1</FareLink></Fare></Fares><Legs><Leg><State>Clean</State><ArrivalStation>DPS</ArrivalStation><DepartureStation>CGK</DepartureStation><STA>2024-03-24T08:05:00</STA><STD>2024-03-24T05:10:00</STD><FlightDesignator><State>New</State><CarrierCode>QZ</CarrierCode><FlightNumber>7510</FlightNumber><OpSuffix> </OpSuffix></FlightDesignator><LegInfo><State>Clean</State><AdjustedCapacity>180</AdjustedCapacity><EquipmentType>320</EquipmentType><EquipmentTypeSuffix>A</EquipmentTypeSuffix><ArrivalTerminal/><ArrivalStationAirportName>Ngurah Rai International Airport</ArrivalStationAirportName><ArrvLTV>480</ArrvLTV><Capacity>180</Capacity><CodeShareIndicator> </CodeShareIndicator><DepartureTerminal>2</DepartureTerminal><DepartureStationAirportName>Soekarno Hatta International Airport</DepartureStationAirportName><DeptLTV>420</DeptLTV><ETicket>false</ETicket><FlifoUpdated>false</FlifoUpdated><IROP>false</IROP><Status>Normal</Status><Lid>180</Lid><OnTime> </OnTime><PaxSTA>2024-03-15T04:03:32.9561821</PaxSTA><PaxSTD>2024-03-15T04:03:32.9561821</PaxSTD><PRBCCode>QZY180</PRBCCode><ScheduleServiceType> </ScheduleServiceType><Sold>6</Sold><OutMoveDays>0</OutMoveDays><BackMoveDays>0</BackMoveDays><LegSSRs/><OperatingFlightNumber>    </OperatingFlightNumber><OperatedByText/><OperatingCarrier/><OperatingOpSuffix> </OperatingOpSuffix><SubjectToGovtApproval>false</SubjectToGovtApproval><MarketingCode>320FIX</MarketingCode><ChangeOfDirection>false</ChangeOfDirection><MarketingOverride>false</MarketingOverride></LegInfo><InventoryLegID>8662581</InventoryLegID></Leg></Legs><PaxSegments><PaxSegment><State>Clean</State><BoardingSequence>0</BoardingSequence><CreatedDate>2024-03-15T04:03:31.503Z</CreatedDate><LiftStatus>Default</LiftStatus><OverBookIndicator/><PassengerNumber>0</PassengerNumber><PriorityDate>2024-03-15T04:03:31.52Z</PriorityDate><TripType>OneWay</TripType><TimeChanged>false</TimeChanged><POS><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode></POS><SourcePOS><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode></SourcePOS><VerifiedTravelDocs/><ModifiedDate>2024-03-15T04:03:31.503Z</ModifiedDate><ActivityDate>2024-03-15T04:03:31.503Z</ActivityDate><BaggageAllowanceWeight>0</BaggageAllowanceWeight><BaggageAllowanceWeightType>Default</BaggageAllowanceWeightType><BaggageAllowanceUsed>false</BaggageAllowanceUsed></PaxSegment></PaxSegments><SalesDate>2024-03-15T04:03:31.503Z</SalesDate><SegmentSellKey>QZ~7510~ ~~CGK~03/24/2024 05:10~DPS~03/24/2024 08:05~~</SegmentSellKey><ChannelType>API</ChannelType></Segment></Segments><JourneySellKey>QZ~7510~ ~~CGK~03/24/2024 05:10~DPS~03/24/2024 08:05~~</JourneySellKey></Journey></Journeys><BookingComments><BookingComment><State>Clean</State><CommentType>Default</CommentType><CommentText>A.C.E</CommentText><PointOfSale><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode></PointOfSale><CreatedDate>2024-03-15T04:03:31.503Z</CreatedDate></BookingComment><BookingComment><State>Clean</State><CommentType>Default</CommentType><CommentText>Modified by API</CommentText><PointOfSale><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode></PointOfSale><CreatedDate>2024-03-15T04:03:31.503Z</CreatedDate></BookingComment></BookingComments><BookingContacts><BookingContact><State>Clean</State><TypeCode>P</TypeCode><Names><BookingName><State>Clean</State><FirstName>Rosyid</FirstName><MiddleName/><LastName>Setyawan</LastName><Suffix/><Title>MR</Title></BookingName></Names><EmailAddress>airdoc_TEJ4KX@doc.nusatrip.com</EmailAddress><HomePhone>6287888881234</HomePhone><WorkPhone>02150118747</WorkPhone><OtherPhone>6287888881234</OtherPhone><Fax/><CompanyName>NUSATRIP</CompanyName><AddressLine1>KH. ABDULLAH SYAFE'I KAV. 27</AddressLine1><AddressLine2/><AddressLine3/><City>JAKARTA</City><ProvinceState/><PostalCode>12810</PostalCode><CountryCode>ID</CountryCode><CultureCode/><DistributionOption>Email</DistributionOption><CustomerNumber/><NotificationPreference>None</NotificationPreference><SourceOrganization>IDTSUKSCGK</SourceOrganization></BookingContact></BookingContacts><NumericRecordLocator>143104252725</NumericRecordLocator><SourceBookingPOS><State>Clean</State><AgentCode>APIIDTUNASS</AgentCode><DomainCode>TOP</DomainCode><LocationCode>KUL</LocationCode><ISOCountryCode/><SourceSystemCode/></SourceBookingPOS></GetBookingResult></GetBookingResponse></s:Body></s:Envelope>";
        return str;
    }

    @PostMapping(value = "/citilink", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> mockcitilink() {

        String str = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><s:Fault><faultcode>s:Client</faultcode><faultstring xml:lang=\"en-US\">The requested class of service is sold out.</faultstring><detail><APIGeneralFault xmlns=\"http://schemas.navitaire.com/WebServices/FaultContracts\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><ErrorCode>1018</ErrorCode><ErrorType>ClassNotAvailable</ErrorType><Message>The requested class of service is sold out.</Message><UniqueID>a34c0ba6-2cfe-4e08-a905-339b9443a0b0</UniqueID><StackTrace i:nil=\"true\"/></APIGeneralFault></detail></s:Fault></s:Body></s:Envelope>";

        return new ResponseEntity<>(str, HttpStatus.OK);
    }

}
