package com.example.Absensi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbsensiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void  testTambah() {
		Calculator calculator = new Calculator();

		double test = calculator.Tambah(2,3);
		Assertions.assertEquals(7, test);
	}
	@Test
	void testKali(){
		Calculator calculator = new Calculator();

		double test = calculator.Kali(2,3);
		Assertions.assertEquals(6,test);
	}

	@Test
	void testKurang(){
		Calculator calculator = new Calculator();

		double test = calculator.Kurang(10,5);
		Assertions.assertEquals(7,test);
	}

	@Test
	void testBagi(){
		Calculator calculator = new Calculator();

		double test = calculator.Bagi(10,5);
		Assertions.assertEquals(2,test);
	}
}
