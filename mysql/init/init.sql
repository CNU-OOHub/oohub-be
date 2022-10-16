CREATE DATABASE oohub;
CREATE USER 'sudoku'@'%' IDENTIFIED BY 'sudoku';

GRANT ALL PRIVILEGES ON oohub.* TO 'sudoku'@'%';