package com.example.listener;

import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.example.model.StudentCsv;
import com.example.model.StudentJson;

@Component
public class SkipListener {

	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		System.out.println(t.getMessage());
		if(t instanceof FlatFileParseException) {
			System.out.println("## Bad Record in reader : "+((FlatFileParseException) t).getInput());
		}
	}
	
	@OnSkipInProcess
	public void onSkipInProcess(StudentCsv studentCsv, Throwable t) {
		System.out.println("## Bad Record in peocessor : "+ studentCsv);
	}
	
	@OnSkipInWrite
	public void onSkipInWrite(StudentJson studentJson, Throwable t) {
		System.out.println("## Bad Record in Writer : "+ studentJson);
	}
}
