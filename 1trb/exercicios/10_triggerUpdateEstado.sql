CREATE OR REPLACE FUNCTION dbo.update_estado_func()
RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE dbo.jogador SET estado = 'Banido' WHERE id = OLD.id;
  RETURN OLD;
END;
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER update_estado_trigger
    INSTEAD OF DELETE ON dbo.jogadorTotalInfo
    FOR EACH ROW
    EXECUTE FUNCTION dbo.update_estado_func();