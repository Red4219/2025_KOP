// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.littletonrobotics.junction.LoggedRobot;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends LoggedRobot {
  // public class Robot extends LoggedRobot {
  private DifferentialDrive m_robotDrive;
  XboxController controller;

  private final WPI_VictorSPX m_leftMotor1 = new WPI_VictorSPX(1);
  private final WPI_VictorSPX m_leftMotor2 = new WPI_VictorSPX(2);
  private final WPI_VictorSPX m_rightMotor3 = new WPI_VictorSPX(3);
  private final WPI_VictorSPX m_rightMotor4 = new WPI_VictorSPX(4);
  
  private final CANSparkMax motor1 = new CANSparkMax(5, MotorType.kBrushless);
  private final CANSparkMax motor2 = new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax motor3 = new CANSparkMax(7, MotorType.kBrushless);

  @Override
  public void robotInit() {
    SendableRegistry.addChild(m_robotDrive, m_leftMotor1);
    SendableRegistry.addChild(m_robotDrive, m_leftMotor2);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor3);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor4);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor3.setInverted(false);

    m_rightMotor4.follow(m_rightMotor3);
    m_leftMotor2.follow(m_leftMotor1);

    m_robotDrive = new DifferentialDrive(m_leftMotor1, m_rightMotor3);

    controller = new XboxController(0);

    motor1.setIdleMode(IdleMode.kCoast);
    motor2.setIdleMode(IdleMode.kCoast);
    motor3.setIdleMode(IdleMode.kCoast);

    motor2.follow(motor1);
    motor3.follow(motor1);

    // Start AdvantageKit logger
    // Logger.start();
  }

  @Override
  public void teleopPeriodic() {
    m_robotDrive.tankDrive(-controller.getLeftY(), -controller.getRightY());

    motor1.setVoltage(controller.getRightTriggerAxis());
    

    /*if(controller.getRightBumperPressed()) {
      motor1.setVoltage(2.0);
      //motor1.set(2.0);
    } else if(controller.getRightBumperReleased()) {
      motor1.stopMotor();
    }*/
  }
}
