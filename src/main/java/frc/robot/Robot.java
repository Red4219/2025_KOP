// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_robotDrive;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  private final WPI_VictorSPX m_leftMotor1 = new WPI_VictorSPX(1);
  private final WPI_VictorSPX m_leftMotor2 = new WPI_VictorSPX(2);
  private final WPI_VictorSPX m_rightMotor3 = new WPI_VictorSPX(3);
  private final WPI_VictorSPX m_rightMotor4 = new WPI_VictorSPX(4);

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
    //m_rightMotor4.setInverted(true);
    

    m_rightMotor4.follow(m_rightMotor3);
    m_leftMotor2.follow(m_leftMotor1);

    m_robotDrive = new DifferentialDrive(m_leftMotor1, m_rightMotor3);
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    m_robotDrive.tankDrive(-m_leftStick.getY(), -m_rightStick.getY());
  }
}
