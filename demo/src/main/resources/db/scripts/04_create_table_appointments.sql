
create table appointments(

    appointment_id bigint not null,
	date1 date not null,
	time1 time not null,
	doctor_dni varchar(10) null,
	nurse_dni varchar(10) null,
	patient_dni varchar(10) not null,


	FOREIGN KEY (doctor_dni) REFERENCES doctors(doctor_dni),
	FOREIGN KEY (nurse_dni) REFERENCES nurses(nurse_dni),
	FOREIGN KEY (patient_dni) REFERENCES patients(patient_dni)



);
